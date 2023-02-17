package pl.salo.stoneglish.presentation.auth.sign_up

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentSignUpStepOneBinding
import pl.salo.stoneglish.util.authNavigator
import pl.salo.stoneglish.presentation.auth.BaseAuthFragment

private const val TAG = "SignUpStepOneFragment"

@AndroidEntryPoint
class SignUpStepOneFragment : BaseAuthFragment<FragmentSignUpStepOneBinding>(
    FragmentSignUpStepOneBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSwitchFragmentListener { authNavigator().goToSignUpStepTwo() }

        with(binding) {
            signUpBackArrow.setOnClickListener {
                authNavigator().goBack()
            }

            signUpBtn.setOnClickListener {
                val email = signUpEmail.text.toString()
                val password = signUpPassword.text.toString()

                viewModel.saveEmailAndPasswordToCache(email, password)
            }

        }
    }
}