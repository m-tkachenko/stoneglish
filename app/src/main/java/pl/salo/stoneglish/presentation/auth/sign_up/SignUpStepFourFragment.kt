package pl.salo.stoneglish.presentation.auth.sign_up

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentSignUpStepFourBinding
import pl.salo.stoneglish.presentation.auth.BaseAuthFragment
import pl.salo.stoneglish.util.navigator

@AndroidEntryPoint
class SignUpStepFourFragment : BaseAuthFragment<FragmentSignUpStepFourBinding>(
    FragmentSignUpStepFourBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSwitchFragmentListener { navigator().goToCoreActivity() }

        with(binding) {
            signUpBackArrow.setOnClickListener { navigator().goBack() }
            signUpSkipBtn.setOnClickListener { viewModel.saveUserInterestedTopics(listOf()) }
        }
    }
}