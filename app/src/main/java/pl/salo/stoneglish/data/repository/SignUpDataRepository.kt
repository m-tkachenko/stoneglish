package pl.salo.stoneglish.data.repository

import android.util.Log
import pl.salo.stoneglish.domain.model.SignUpData

/**
 * Repository holds sign up data in the app memory
 */

const val TAG = "SignUpDataRepository"
class SignUpDataRepository {

    private val signUpData = SignUpData()

    fun setEmailAndPassword(email: String, password: String){
        signUpData.email = email
        signUpData.password = password

        Log.d(TAG, "setEmailAndPassword, DATA: $signUpData")
    }

    fun setAgeAndName(name: String, age:Int){
        signUpData.age = age
        signUpData.username = name

        Log.d(TAG, "setAgeAndName, DATA: $signUpData")
    }

    fun setEnglishLevel(e_level: String){
        signUpData.englishLevel = e_level

        Log.d(TAG, "setEnglishLevel, DATA: $signUpData")
    }

    fun setInterestedTopics(topics: List<String>){
        signUpData.interestedTopics = topics

        Log.d(TAG, "setInterestedTopics, DATA: $signUpData")
    }

    fun getSignUpData() = signUpData
}