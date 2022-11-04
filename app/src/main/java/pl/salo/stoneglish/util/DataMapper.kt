package pl.salo.stoneglish.util

import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.model.SignUpData

class DataMapper {
    fun signUpDataToUser(signUpData: SignUpData): User {
        val topics = signUpData.interestedTopics.map { it.title.lowercase() } as MutableList
        if(topics.isEmpty()) topics.add("all")
        return User(
            id = signUpData.id ?: "",
            email = signUpData.email ?: "",
            username = signUpData.username ?: "",
            age = signUpData.age ?: 0,
            interestedTopics = topics,
            englishLevel = signUpData.englishLevel ?: ""
        )
    }
}