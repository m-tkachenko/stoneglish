package pl.salo.stoneglish.domain.services

import pl.salo.stoneglish.data.model.User

interface DatabaseService {
    suspend fun writeUserData(user: User)
    suspend fun getUser(id:String): User
}