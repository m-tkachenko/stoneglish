package pl.salo.stoneglish.presentation.auth.sign_up

import android.os.Bundle
import android.view.View
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
        }
    }
}