package pl.salo.stoneglish.util

import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import pl.salo.stoneglish.presentation.core.CoreActivity
import pl.salo.stoneglish.presentation.core.TextToSpeechResult
import pl.salo.stoneglish.presentation.core.home.dialog.AddNewCardDialog

interface CoreNavigator {
    fun goToAuthActivity()
    fun goToTopicFragment()
    fun speakWithFlow(text: String): Flow<TextToSpeechResult>
    fun setClickableWords(content:String, textView: TextView)

    fun goBack()
    fun goToCard(module: String)
    fun goToModules()

    fun showAddCardDialog(dialog: AddNewCardDialog)

    fun signOut()

    fun makeToast(text: String)
    fun makeSnack(text: String)
    fun goToCreateModule()
}

fun Fragment.coreNavigator(): CoreNavigator {
    return requireActivity() as CoreActivity
}