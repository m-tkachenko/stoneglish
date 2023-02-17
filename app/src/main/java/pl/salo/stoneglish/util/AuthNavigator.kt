package pl.salo.stoneglish.util

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.fragment.app.Fragment

interface AuthNavigator {
    fun goToSignUp()
    fun goToSignUpStepTwo()
    fun goToSignUpStepThree()
    fun goToSignUpStepFour()
    fun goToSignIn()
    fun goBack()
    fun goToCoreActivity()
    fun goToEnglishTest()

    fun setProgressDialog(state: ProgressDialogState, text: String? = null)

    fun makeToast(text: String)
    fun makeSnack(text: String)

}

enum class ProgressDialogState {
    SHOW,
    HIDE
}

fun Fragment.authNavigator(): AuthNavigator {
    return requireActivity() as AuthNavigator
}