package pl.salo.stoneglish.presentation.auth.sign_up

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentSignUpStepTwoBinding
import pl.salo.stoneglish.presentation.auth.BaseAuthFragment
import pl.salo.stoneglish.util.authNavigator

@AndroidEntryPoint
class SignUpStepTwoFragment :
    BaseAuthFragment<FragmentSignUpStepTwoBinding>(FragmentSignUpStepTwoBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSwitchFragmentListener { authNavigator().goToSignUpStepThree() }

        with(binding) {
            signUpBackArrow.setOnClickListener {
                authNavigator().goBack()
            }
            signUpContinueBtn.setOnClickListener {
                val age = binding.signUpAge.text.toString()
                val name = binding.signUpName.text.toString()
                viewModel.saveNameAndAgeToCache(name, age)
            }
        }
    }
}