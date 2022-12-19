package pl.salo.stoneglish.util

import androidx.annotation.DrawableRes

object Utils {
    fun Any?.isNull() = this == null
    fun Any?.isNotNull() = this != null
    @DrawableRes
    fun String.getTopicIcon(): Int =
        when(this) {
            "Art" -> Topic.Art.icon
            "Books" -> Topic.Books.icon
            "Celebrities" -> Topic.Celebrities.icon
            "Countries" -> Topic.Countries.icon
            "Education" -> Topic.Education.icon
            "Films" -> Topic.Films.icon
            "Holidays" -> Topic.Holidays.icon
            "Lifehack" -> Topic.Lifehack.icon
            "Places" -> Topic.Places.icon
            "Shopping" -> Topic.Shopping.icon
            "Sport" -> Topic.Sport.icon
            "Traveling" -> Topic.Traveling.icon
            "Work" -> Topic.Work.icon
            else -> Topic.Default.icon
        }
}