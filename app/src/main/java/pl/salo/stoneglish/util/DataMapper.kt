package pl.salo.stoneglish.util

import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.model.SignUpData

class DataMapper {
    fun signUpDataToUser(signUpData: SignUpData): User {
        return User(
            id = signUpData.id ?: "",
            email = signUpData.email ?: "",
            username = signUpData.username ?: "",
            age = signUpData.age ?: 0,
            interestedTopics = signUpData.interestedTopics ?: listOf(),
            englishLevel = signUpData.englishLevel ?: ""
        )
    }
}