package pl.salo.stoneglish.domain.services

import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.model.card.TestForCards

interface DatabaseService {
    suspend fun writeUserData(user: User)
    suspend fun getUser(id:String): User

    suspend fun writeNewTopic(topic: Topic)

    suspend fun writeUserCard(card: Card, module: String, userId: String)
    suspend fun writeUserCards(cards: List<Card>, module: String, userId: String)

    suspend fun listOfCards(moduleName: String, userId: String): List<Card>
    suspend fun listOfModules(userId: String): List<Pair<Int, String>>
    suspend fun listOfTests(moduleName: String, userId: String): List<TestForCards>

    suspend fun getListOfDailyCards(): List<Card>
    suspend fun getListOfPolishWords(): List<String>
    suspend fun writeUserCategories(userId: String, categories: List<String>)
    suspend fun changeUserField(userId: String, field: String, newValue: String)
}