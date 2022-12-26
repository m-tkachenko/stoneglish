package pl.salo.stoneglish.util

import androidx.fragment.app.Fragment
import pl.salo.stoneglish.presentation.core.CoreActivity
import pl.salo.stoneglish.presentation.core.home.dialog.AddNewCardDialog

interface CoreNavigator {
    fun goToAuthActivity()
    fun goBack()
    fun goToCard(module: String)

    fun showAddCardDialog(dialog: AddNewCardDialog)

    fun signOut()

    fun makeToast(text: String)
    fun makeSnack(text: String)
}

fun Fragment.coreNavigator(): CoreNavigator {
    return requireActivity() as CoreActivity
}