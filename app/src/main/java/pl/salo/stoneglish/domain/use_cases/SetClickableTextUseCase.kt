package pl.salo.stoneglish.domain.use_cases

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.presentation.core.LinkMovementMethodOverride
import java.text.BreakIterator
import java.util.*
import javax.inject.Inject

class SetClickableTextUseCase @Inject constructor() {
    @SuppressLint("ClickableViewAccessibility")
    operator fun invoke(content: String, textView: TextView): Flow<Resource<String>> = callbackFlow {

        try {
            val definition = content.trim { it <= ' ' }

            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.setText(definition, TextView.BufferType.SPANNABLE)
            val spans = textView.text as Spannable
            val iterator: BreakIterator = BreakIterator.getWordInstance(Locale.US)
            iterator.setText(definition)
            var start: Int = iterator.first()

            var end: Int = iterator.next()
            while (end != BreakIterator.DONE) {
                val possibleWord = definition.substring(start, end)
                if (Character.isLetterOrDigit(possibleWord[0])) {

                    val clickSpan = object : ClickableSpan() {
                        override fun onClick(p0: View) {
                            trySend(Resource.Success(possibleWord))
                            Log.d("getClickableSpan", "Clicked word is : $possibleWord")
                        }
                    }
                    spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                start = end
                end = iterator.next()

            }
            textView.setOnTouchListener(LinkMovementMethodOverride())

        } catch (e: Exception) {
            send(Resource.Error(null, e.message)).also { close() }
        }

        awaitClose ()
    }
}