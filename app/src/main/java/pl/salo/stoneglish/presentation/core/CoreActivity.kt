package pl.salo.stoneglish.presentation.core

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import pl.salo.stoneglish.R
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.ActivityCoreBinding
import pl.salo.stoneglish.presentation.auth.AuthActivity
import pl.salo.stoneglish.presentation.auth.AuthViewModel
import pl.salo.stoneglish.presentation.core.cards.fragments.CardsFragment
import pl.salo.stoneglish.presentation.core.cards.fragments.ModulesFragment
import pl.salo.stoneglish.presentation.core.dictionary.DictionaryFragment
import pl.salo.stoneglish.presentation.core.home.HomeFragment
import pl.salo.stoneglish.presentation.core.home.TopicFragment
import pl.salo.stoneglish.presentation.core.profile.ProfileFragment
import pl.salo.stoneglish.util.CoreNavigator
import java.util.*

const val TAG = "CoreActivity"

@AndroidEntryPoint
class CoreActivity : AppCompatActivity(), CoreNavigator, TextToSpeech.OnInitListener {

    lateinit var binding: ActivityCoreBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var speaker: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        speaker = TextToSpeech(this, this)
        observeSignOut()
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.dictionary -> {
                    replaceFragment(DictionaryFragment())
                    true
                }
                R.id.cards -> {
                    replaceFragment(ModulesFragment())
                    true
                }
                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> {
                    true
                }
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.home

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.coreFragmentContainer, fragment)
            .commit()
    }

    private fun observeSignOut(){
        viewModel.onSignOut.observe(this){
            when(it){
                is Resource.Success -> {
                    goToAuthActivity()
                }
                is Resource.Error -> {
                    makeToast(it.message.toString())
                }
                is Resource.Loading -> {
                    //TODO
                }
            }
        }
    }

    private fun showSignOutDialog() {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sign Out")
        builder.setMessage("Do you want to sign out?")
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.signOut()
        }
        builder.setNegativeButton("No") { _, _ ->
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }

    override fun signOut() {
        showSignOutDialog()
    }

    override fun makeToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() = goBack()
    override fun goToAuthActivity() {
        val intent = Intent(this@CoreActivity, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun goToTopicFragment() {
        replaceFragment(TopicFragment())
    }

    override fun speak(text: String): Flow<TextToSpeechResult> = callbackFlow {
        speaker.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED
        )

        val progressListener = object : UtteranceProgressListener() {
            override fun onStart(p0: String?) {
                trySend(TextToSpeechResult.Loading)
            }

            override fun onDone(p0: String?) {
                trySend(TextToSpeechResult.Done).also { close() }
            }

            @Deprecated("Deprecated in Java", ReplaceWith("trySend(TextToSpeechResult.Error)"))
            override fun onError(p0: String?) {
                trySend(TextToSpeechResult.Error).also { close() }
            }

        }
        speaker.setOnUtteranceProgressListener(progressListener)

        awaitClose()

    }


    override fun goBack() {
        if (supportFragmentManager.backStackEntryCount != 0) supportFragmentManager.popBackStack() else finish()
    }

    override fun goToCard(module: String) {
        val bundle = Bundle()
        bundle.putString("ModuleName", module)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.coreFragmentContainer, CardsFragment::class.java, bundle)
            .addToBackStack("module")
            .commit()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {

            val result = speaker.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "The Language specified is not supported!")
            }
        } else {
            Log.e(TAG, "Initialization Failed!")
        }
    }

    override fun onDestroy() {
        speaker.stop()
        speaker.shutdown()
        super.onDestroy()
    }
}

sealed class TextToSpeechResult {
    object Done : TextToSpeechResult()
    object Loading : TextToSpeechResult()
    object Error : TextToSpeechResult()
}