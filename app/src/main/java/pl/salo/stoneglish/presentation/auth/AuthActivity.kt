package pl.salo.stoneglish.presentation.auth

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.R
import pl.salo.stoneglish.databinding.ActivityAuthBinding
import pl.salo.stoneglish.presentation.EnglishTestFragment
import pl.salo.stoneglish.presentation.auth.sign_in.ForgotPasswordFragment
import pl.salo.stoneglish.presentation.auth.sign_in.SignInFragment
import pl.salo.stoneglish.presentation.auth.sign_up.SignUpStepFourFragment
import pl.salo.stoneglish.presentation.auth.sign_up.SignUpStepOneFragment
import pl.salo.stoneglish.presentation.auth.sign_up.SignUpStepThreeFragment
import pl.salo.stoneglish.presentation.auth.sign_up.SignUpStepTwoFragment
import pl.salo.stoneglish.presentation.auth.welcome.WelcomeFragment
import pl.salo.stoneglish.presentation.core.CoreActivity
import pl.salo.stoneglish.util.AuthNavigator
import pl.salo.stoneglish.util.ProgressDialogState
import javax.inject.Inject

private const val TAG = "AuthActivity"

@AndroidEntryPoint
class AuthActivity : AppCompatActivity(), AuthNavigator {
    private lateinit var binding: ActivityAuthBinding
    private val authViewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.authFragmentContainer, WelcomeFragment())
            .commit()
    }

    override fun goToSignUp() = addFragmentToStack(SignUpStepOneFragment())

    override fun goToSignUpStepTwo() = addFragmentToStack(SignUpStepTwoFragment())

    override fun goToSignUpStepThree() = addFragmentToStack(SignUpStepThreeFragment())

    override fun goToSignUpStepFour() = addFragmentToStack(SignUpStepFourFragment())

    override fun goToSignIn() = addFragmentToStack(SignInFragment())

    override fun goBack() = supportFragmentManager.popBackStack()


    override fun goToCoreActivity() {
        startActivity(Intent(this@AuthActivity, CoreActivity::class.java))
        finish()
    }

    override fun goToEnglishTest() {
        addFragmentToStack(EnglishTestFragment())
    }

    override fun setProgressDialog(state: ProgressDialogState, text: String?) {
        when (state) {
            ProgressDialogState.HIDE -> {
                progressDialog.dismiss()
            }
            else -> {
                progressDialog.show()
                progressDialog.findViewById<TextView>(R.id.loadingLayoutText)?.text = text
            }
        }
    }

    override fun makeToast(text: String) {
        Toast
            .makeText(this, text, Toast.LENGTH_LONG)
            .show()
    }

    override fun makeSnack(text: String) {
        Snackbar
            .make(binding.root, text, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun goToForgotPassword() {
        addFragmentToStack(ForgotPasswordFragment())
    }

    private fun addFragmentToStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.authFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() = goBack()
}