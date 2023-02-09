package pl.salo.stoneglish.presentation.auth.sign_up

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentSignUpStepThreeBinding
import pl.salo.stoneglish.presentation.auth.BaseAuthFragment
import pl.salo.stoneglish.util.Constants
import pl.salo.stoneglish.util.authNavigator

@AndroidEntryPoint
class SignUpStepThreeFragment : BaseAuthFragment<FragmentSignUpStepThreeBinding>(
    FragmentSignUpStepThreeBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSwitchFragmentListener { authNavigator().goToSignUpStepFour() }

        with(binding) {
            signUpBackArrow.setOnClickListener { authNavigator().goBack() }
            signUpBeginnerBtn.setOnClickListener { writeUserLanguageLevel(Constants.ENGLISH_LEVEL_BEGINNER) }
            signUpIntermediateBtn.setOnClickListener { writeUserLanguageLevel(Constants.ENGLISH_LEVEL_INTERMEDIATE) }
            signUpAdvancedBtn.setOnClickListener { writeUserLanguageLevel(Constants.ENGLISH_LEVEL_ADVANCED) }
            signUpTakeTestBtn.setOnClickListener { authNavigator().goToEnglishTest() }
        }
    }

    private fun writeUserLanguageLevel(level: String) {
        viewModel.saveUserEnglishLevelToCache(level)
    }
}