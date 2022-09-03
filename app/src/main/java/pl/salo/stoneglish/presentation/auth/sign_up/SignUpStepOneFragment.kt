package pl.salo.stoneglish.presentation.auth.sign_up

import android.os.Bundle
import android.util.Log
import android.view.View
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentSignUpStepOneBinding
import pl.salo.stoneglish.util.navigator
import pl.salo.stoneglish.presentation.auth.BaseAuthFragment

private const val TAG = "SignUpStepOneFragment"

@AndroidEntryPoint
class SignUpStepOneFragment : BaseAuthFragment<FragmentSignUpStepOneBinding>(
    FragmentSignUpStepOneBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSwitchFragmentListener { navigator().goToSignUpStepTwo() }

        with(binding) {
            signUpBackArrow.setOnClickListener {
                navigator().goBack()
            }

            signUpBtn.setOnClickListener {
                val email = signUpEmail.text.toString()
                val password = signUpPassword.text.toString()

                viewModel.saveEmailAndPasswordToCache(email, password)
            }

            signUpWithGoogle.setOnClickListener {
                navigator().beginGoogleSignIn()
            }

            signUpWithFacebook.setPermissions("email", "public_profile")
            signUpWithFacebook.setFragment(this@SignUpStepOneFragment)
            signUpWithFacebook.registerCallback(
                navigator().getFacebookCallbackManager(),
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult) {
                        Log.d(TAG, "FacebookSignIn : Success")
                        viewModel.signInUsingFacebook(result.accessToken)
                    }

                    override fun onCancel() {
                        Log.d(TAG, "FacebookSignIn : Cancel")
                    }

                    override fun onError(error: FacebookException) {
                        Log.e(TAG, "FacebookSignIn : Failure : Error = $error")
                    }
                })
        }
    }
}