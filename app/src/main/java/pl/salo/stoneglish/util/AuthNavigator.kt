package pl.salo.stoneglish.util

import androidx.fragment.app.Fragment

interface AuthNavigator {
    fun goToSignUp()
    fun goToSignIn()
    fun goBack()
    fun goToCoreActivity()
    fun setProgressDialog(state: ProgressDialogState, text: String? = null)
}

enum class ProgressDialogState {
    SHOW,
    HIDE
}

fun Fragment.navigator(): AuthNavigator {
    return requireActivity() as AuthNavigator
}