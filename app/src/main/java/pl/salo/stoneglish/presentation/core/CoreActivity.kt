package pl.salo.stoneglish.presentation.core

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import pl.salo.stoneglish.R
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.Keyword
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.databinding.ActivityCoreBinding
import pl.salo.stoneglish.domain.model.card.TestType
import pl.salo.stoneglish.presentation.auth.AuthActivity
import pl.salo.stoneglish.presentation.auth.AuthViewModel
import pl.salo.stoneglish.presentation.core.admin.AddTopicFragment
import pl.salo.stoneglish.presentation.core.cards.fragments.*
import pl.salo.stoneglish.presentation.auth.sign_up.SignUpStepFourFragment
import pl.salo.stoneglish.presentation.core.cards.fragments.CardsFragment
import pl.salo.stoneglish.presentation.core.cards.fragments.CardsMemoTestFragment
import pl.salo.stoneglish.presentation.core.cards.fragments.CreateModuleFragment
import pl.salo.stoneglish.presentation.core.cards.fragments.ModulesFragment
import pl.salo.stoneglish.presentation.core.dictionary.DictionaryFragment
import pl.salo.stoneglish.presentation.core.home.HomeFragment
import pl.salo.stoneglish.presentation.core.home.TopicFragment
import pl.salo.stoneglish.presentation.core.profile.ChangeFieldFragment
import pl.salo.stoneglish.presentation.core.home.dialog.AddNewCardDialog
import pl.salo.stoneglish.presentation.core.profile.ProfileFragment
import pl.salo.stoneglish.presentation.core.profile.information.InformationFragment
import pl.salo.stoneglish.util.CoreNavigator
import java.util.*


const val TAG = "CoreActivity"

@AndroidEntryPoint
class CoreActivity : AppCompatActivity(), CoreNavigator, TextToSpeech.OnInitListener {

    lateinit var binding: ActivityCoreBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val translateViewModel: TranslateViewModel by viewModels()
    private val viewModel: CoreViewModel by viewModels()
    private lateinit var speaker: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        translateViewModel
        observeClickedWord()
        observeTranslatedWord()

        binding.closeActionButton.setOnClickListener {
            binding.topWordContainer.visibility = View.GONE
        }
        binding.audioActionButton.setOnClickListener {
            speak(binding.clickedWord.text.toString())
        }

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

    private fun addFragmentToStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.coreFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun observeSignOut() {
        authViewModel.onSignOut.observe(this) {
            when (it) {
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
            authViewModel.signOut()
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
    override fun makeSnack(text: String) {
        Snackbar
            .make(binding.root, text, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun goToCreateModule() {
        addFragmentToStack(CreateModuleFragment())
    }

    override fun goToInformation() {
        addFragmentToStack(InformationFragment())
    }

    override fun goToTest(type: TestType) {
        val fragment = if(type == TestType.MEMORIZATION) CardsMemoTestFragment() else TestCardsFragment()
        addFragmentToStack(fragment)
    }

    override fun onBackPressed() = goBack()
    override fun goToAuthActivity() {
        val intent = Intent(this@CoreActivity, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun goToTopicFragment() {
        addFragmentToStack(TopicFragment())
    }
    override fun goToAddTopicFragment() {
        addFragmentToStack(AddTopicFragment())
    }

    override fun speakWithFlow(text: String): Flow<TextToSpeechResult> = callbackFlow {
        speak(text)

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

    private fun speak(text:String){
        speaker.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED
        )
    }

    override fun setClickableWords(content: String, textView: TextView) {
        viewModel.setClickableText(content, textView)
    }

    override fun setKeywordAddAction(onPlusClick: (Keyword) -> Unit) {
        binding.plusActionButton.setOnClickListener {
            onPlusClick(
                Keyword(
                    word = binding.clickedWord.text.toString(),
                    phonetic = "",
                    translate = binding.translatedWord.text.toString()
                )
            )
            makeToast("Keyword added")
        }
    }

    override fun goBack() {
        if (supportFragmentManager.backStackEntryCount != 0) supportFragmentManager.popBackStack() else finish()
    }

    override fun goToCard(module: String) {
        val bundle = Bundle()
        bundle.putString("ModuleName", module)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.coreFragmentContainer,
                CardsFragment::class.java,
                bundle
            )
            .addToBackStack("module")
            .commit()
    }

    override fun showAddCardDialog(dialog: AddNewCardDialog) {
        dialog.show(supportFragmentManager, "add_new_card")
    }

    override fun showAddedTopicDialog() {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Topic added")
        builder.setMessage("Do you want to add one more topic to this view type?")
        builder.setPositiveButton("Yes") { _, _ ->
            replaceFragment(AddTopicFragment())
            dialog?.dismiss()
        }
        builder.setNegativeButton("No") { _, _ ->
            finish()
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }

    override fun goToModules() {
        replaceFragment(ModulesFragment())
    }

    override fun goToChangeFieldScreen() {
        addFragmentToStack(ChangeFieldFragment())
    }

    override fun goToFavoriteTopics() {
        addFragmentToStack(SignUpStepFourFragment())
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

    private fun observeClickedWord() {
        viewModel.word.observe(this) { word ->
            binding.clickedWord.text = word.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            translateViewModel.translate(word)
        }
    }

    private fun observeTranslatedWord() {
        translateViewModel.translatedWord.observe(this) { word ->
            binding.translatedWord.text = word.lowercase()
            binding.topWordContainer.visibility = View.VISIBLE
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