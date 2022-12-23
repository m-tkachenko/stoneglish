package pl.salo.stoneglish.util

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import pl.salo.stoneglish.presentation.core.CoreActivity
import pl.salo.stoneglish.presentation.core.TextToSpeechResult

interface CoreNavigator {
    fun goToAuthActivity()
    fun goToTopicFragment()
    fun speak(text: String): Flow<TextToSpeechResult>

    fun goBack()
    fun goToCard(module: String)

    fun signOut()
    fun makeToast(text: String)
}

fun Fragment.coreNavigator(): CoreNavigator {
    return requireActivity() as CoreActivity
}