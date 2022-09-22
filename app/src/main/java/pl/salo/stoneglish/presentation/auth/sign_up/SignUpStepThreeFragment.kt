package pl.salo.stoneglish.presentation.auth.sign_up

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentSignUpStepThreeBinding
import pl.salo.stoneglish.presentation.auth.BaseAuthFragment
import pl.salo.stoneglish.util.Constants
import pl.salo.stoneglish.util.navigator

@AndroidEntryPoint
class SignUpStepThreeFragment : BaseAuthFragment<FragmentSignUpStepThreeBinding>(
    FragmentSignUpStepThreeBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSwitchFragmentListener { navigator().goToSignUpStepFour() }

        with(binding) {
            signUpBackArrow.setOnClickListener { navigator().goBack() }
            signUpBeginnerBtn.setOnClickListener { writeUserLanguageLevel(Constants.ENGLISH_LEVEL_BEGINNER) }
            signUpIntermediateBtn.setOnClickListener { writeUserLanguageLevel(Constants.ENGLISH_LEVEL_INTERMEDIATE) }
            signUpAdvancedBtn.setOnClickListener { writeUserLanguageLevel(Constants.ENGLISH_LEVEL_ADVANCED) }
            signUpTakeTestBtn.setOnClickListener { /*go take test*/ }
        }
    }

    private fun writeUserLanguageLevel(level: String) {
        viewModel.saveUserEnglishLevelToCache(level)
    }
}