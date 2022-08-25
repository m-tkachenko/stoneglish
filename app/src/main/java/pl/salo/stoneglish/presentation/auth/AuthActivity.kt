package pl.salo.stoneglish.presentation.auth

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.R
import pl.salo.stoneglish.databinding.ActivityAuthBinding
import pl.salo.stoneglish.presentation.auth.sign_in.SignInFragment
import pl.salo.stoneglish.presentation.auth.sign_up.SignUpStepOneFragment
import pl.salo.stoneglish.presentation.auth.welcome.WelcomeFragment
import pl.salo.stoneglish.util.AuthNavigator
import pl.salo.stoneglish.util.ProgressDialogState
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity(), AuthNavigator {
    lateinit var binding: ActivityAuthBinding

    @Inject
    lateinit var progressDialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.authFragmentContainer, WelcomeFragment())
            .commit()
    }

    override fun goToSignUp() {
        addFragmentToStack(SignUpStepOneFragment())
    }

    override fun goToSignIn() {
        addFragmentToStack(SignInFragment())
    }

    override fun goBack() {
        supportFragmentManager.popBackStack()
    }

    override fun goToCoreActivity() {
        TODO("Not yet implemented")
    }

    override fun setProgressDialog(state: ProgressDialogState, text: String?) {
        when(state){
            ProgressDialogState.HIDE->{
                progressDialog.dismiss()
            }
            else -> {
                progressDialog.show()
                progressDialog.findViewById<TextView>(R.id.loadingLayoutText)?.text = text
            }
        }

    }

    private fun addFragmentToStack(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.authFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}