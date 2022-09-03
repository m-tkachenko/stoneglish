package pl.salo.stoneglish.util

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager

interface AuthNavigator {
    fun goToSignUp()
    fun goToSignIn()
    fun goBack()
    fun goToCoreActivity()

    fun setProgressDialog(state: ProgressDialogState, text: String? = null)

    fun makeToast(text: String)
    fun makeSnack(text: String)

    fun activityLauncherResult(): ActivityResultLauncher<IntentSenderRequest>
    fun beginGoogleSignIn()
    fun getFacebookCallbackManager(): CallbackManager
}

enum class ProgressDialogState {
    SHOW,
    HIDE
}

fun Fragment.navigator(): AuthNavigator {
    return requireActivity() as AuthNavigator
}