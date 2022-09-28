package pl.salo.stoneglish.domain.repository

import pl.salo.stoneglish.data.model.User

interface DatabaseRepository {
    suspend fun writeUserData(user: User)
}