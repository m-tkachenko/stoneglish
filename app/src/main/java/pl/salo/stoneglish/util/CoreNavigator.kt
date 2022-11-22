package pl.salo.stoneglish.util

import androidx.fragment.app.Fragment
import pl.salo.stoneglish.presentation.core.CoreActivity

interface CoreNavigator {
    fun goToAuthActivity()

    fun goBack()

    fun signOut()
    fun makeToast(text: String)
}

fun Fragment.coreNavigator(): CoreNavigator {
    return requireActivity() as CoreActivity
}