package pl.salo.stoneglish.util

import androidx.fragment.app.Fragment

interface CoreNavigator {
    fun goBack()
    fun goToCard(module: String)
}

fun Fragment.coreNavigator(): CoreNavigator {
    return requireActivity() as CoreNavigator
}