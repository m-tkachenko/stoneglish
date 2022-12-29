package pl.salo.stoneglish.util

import androidx.fragment.app.Fragment
import pl.salo.stoneglish.presentation.core.CoreActivity

interface CoreNavigator {
    fun goToAuthActivity()
    fun goToTopicFragment()

    fun goBack()
    fun goToCard(module: String)

    fun signOut()
    fun makeToast(text: String)
}

fun Fragment.coreNavigator(): CoreNavigator {
    return requireActivity() as CoreActivity
}