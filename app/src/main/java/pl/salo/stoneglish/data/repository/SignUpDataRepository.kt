package pl.salo.stoneglish.data.repository

import android.util.Log
import pl.salo.stoneglish.R
import pl.salo.stoneglish.domain.model.auth.SignUpCategoryItem
import pl.salo.stoneglish.domain.model.auth.SignUpData

/**
 * Repository holds sign up data in the app memory
 */

const val TAG = "SignUpDataRepository"

class SignUpDataRepository {

    private val signUpData = SignUpData()

    private val categories: MutableList<SignUpCategoryItem> = mutableListOf(
        SignUpCategoryItem("Sport", false, R.drawable.ic_sport),
        SignUpCategoryItem("Traveling", false, R.drawable.ic_traveling),
        SignUpCategoryItem("Books", false, R.drawable.ic_books),
        SignUpCategoryItem("Health", false, R.drawable.ic_health),
        SignUpCategoryItem("Education", false, R.drawable.ic_education),
        SignUpCategoryItem("Countries", false, R.drawable.ic_countries),
        SignUpCategoryItem("Art", false, R.drawable.ic_art),
        SignUpCategoryItem("Films", false, R.drawable.ic_films),
        SignUpCategoryItem("Lifehack", false, R.drawable.ic_lifehack),
        SignUpCategoryItem("Celebrities", false, R.drawable.ic_celebrities),
        SignUpCategoryItem("Holidays", false, R.drawable.ic_holidays),
        SignUpCategoryItem("Shopping", false, R.drawable.ic_shopping),
        SignUpCategoryItem("Work", false, R.drawable.ic_work),
        SignUpCategoryItem("Places", false, R.drawable.ic_places)
    )

    fun setEmailAndPassword(email: String, password: String) {
        signUpData.email = email
        signUpData.password = password

        Log.d(TAG, "setEmailAndPassword, DATA: $signUpData")
    }

    fun setAgeAndName(name: String, age: Int) {
        signUpData.age = age
        signUpData.username = name

        Log.d(TAG, "setAgeAndName, DATA: $signUpData")
    }

    fun setEnglishLevel(eLevel: String) {
        signUpData.englishLevel = eLevel

        Log.d(TAG, "setEnglishLevel, DATA: $signUpData")
    }

    fun getSignUpData() = signUpData

    fun getCategories(): List<SignUpCategoryItem> {
        return categories
    }

    fun setCategoryState(category: SignUpCategoryItem) {
        val index = categories.indexOf(category)
        categories[index] = category

        if(category.isFavorite){
            signUpData.interestedTopics.add(category)
        }else{
            signUpData.interestedTopics.remove(category)
        }

    }

    fun clearCategories() {
        signUpData.interestedTopics = mutableListOf()
        categories.forEach {
            it.isFavorite = false
        }
    }
}