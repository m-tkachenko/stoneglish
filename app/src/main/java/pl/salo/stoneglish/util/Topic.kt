package pl.salo.stoneglish.util

import androidx.annotation.DrawableRes
import pl.salo.stoneglish.R

enum class Topic(@DrawableRes val icon: Int) {
    Art(R.drawable.ic_art),
    Books(R.drawable.ic_books),
    Celebrities(R.drawable.ic_celebrities),
    Countries(R.drawable.ic_countries),
    Education(R.drawable.ic_education),
    Films(R.drawable.ic_films),
    Holidays(R.drawable.ic_holidays),
    Lifehack(R.drawable.ic_lifehack),
    Places(R.drawable.ic_places),
    Shopping(R.drawable.ic_shopping),
    Sport(R.drawable.ic_sport),
    Traveling(R.drawable.ic_traveling),
    Work(R.drawable.ic_work),
    Default(R.color.main_green)
}