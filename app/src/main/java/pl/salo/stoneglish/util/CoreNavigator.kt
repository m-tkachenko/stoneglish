package pl.salo.stoneglish.util

import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import pl.salo.stoneglish.data.model.home.Keyword
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.presentation.core.CoreActivity
import pl.salo.stoneglish.presentation.core.TextToSpeechResult
import pl.salo.stoneglish.presentation.core.home.dialog.AddNewCardDialog

interface CoreNavigator {
    fun goBack()
    fun goToAuthActivity()
    fun goToTopicFragment()
    fun goToAddTopicFragment()
    fun goToCard(module: String)
    fun goToModules()
    fun goToCreateModule()

    fun speakWithFlow(text: String): Flow<TextToSpeechResult>
    fun setClickableWords(content:String, textView: TextView)
    fun setKeywordAddAction(onPlusClick: (Keyword) -> Unit)

    fun showAddCardDialog(dialog: AddNewCardDialog)
    fun showAddedTopicDialog()

    fun signOut()

    fun makeToast(text: String)
    fun makeSnack(text: String)

}

fun Fragment.coreNavigator(): CoreNavigator {
    return requireActivity() as CoreActivity
}