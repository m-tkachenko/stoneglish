package pl.salo.stoneglish.domain.repository

import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.model.card.Test

interface DatabaseRepository {
    suspend fun getUserFlowable(id: String): User
    suspend fun writeUserData(user: User)

    suspend fun writeUserCard(card: Card, module: String)

    suspend fun readCardsList(moduleName: String): List<Card>
    suspend fun readModulesList(): List<String>
    suspend fun readTestsList(moduleName: String): List<Test>
}