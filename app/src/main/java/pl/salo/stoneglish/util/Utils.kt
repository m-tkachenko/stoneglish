package pl.salo.stoneglish.util

import android.view.View
import androidx.annotation.DrawableRes
import pl.salo.stoneglish.data.model.home.Topic

object Utils {
    fun Any?.isNull() = this == null
    fun Any?.isNotNull() = this != null

    fun Boolean.isTrue() = this
    fun Pair<Boolean, Boolean>.isAbsoluteTrue() = first.isTrue() && second.isTrue()

    fun View.ninja(visibility: Boolean) {
        if (visibility)
            this.visibility = View.VISIBLE
        else
            this.visibility = View.GONE
    }
    infix fun View.visible(visibility: Boolean) {
        if (visibility)
            this.visibility = View.VISIBLE
        else
            this.visibility = View.INVISIBLE
    }

    infix fun Any?.same(toCompare: Any?) = this == toCompare
    infix fun Any?.notSame(toCompare: Any?) = this != toCompare
}