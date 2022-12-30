package pl.salo.stoneglish.presentation.core

import android.annotation.SuppressLint
import android.text.Layout
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import java.util.*


class LinkMovementMethodOverride : View.OnTouchListener {
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val widget = v as TextView
        val text = widget.text

        if (text is Spanned) {

            val action: Int = event?.action ?: 0
            if (action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_DOWN
            ) {

                var x = event?.x ?: 0.0f
                var y = event?.y ?: 0.0f

                x -= widget.totalPaddingLeft
                y -= widget.totalPaddingTop
                x += widget.scrollX
                y += widget.scrollY
                val layout: Layout = widget.layout
                val line: Int = layout.getLineForVertical(y.toInt())
                val off: Int = layout.getOffsetForHorizontal(line, x)

                val link = text.getSpans(
                    off, off,
                    ClickableSpan::class.java
                )

                if (link.isNotEmpty()) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget)
                    }

                    return true
                }
            }
        }
        return false
    }

}