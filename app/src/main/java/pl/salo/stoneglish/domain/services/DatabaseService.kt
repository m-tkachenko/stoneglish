package pl.salo.stoneglish.domain.services

import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.model.card.Test

interface DatabaseService {
    suspend fun writeUserData(user: User)
    suspend fun getUser(id:String): User

    suspend fun writeUserCard(card: Card, module: String)

    suspend fun listOfCards(moduleName: String): List<Card>
    suspend fun listOfModules(): List<String>
    suspend fun listOfTests(moduleName: String): List<Test>
}