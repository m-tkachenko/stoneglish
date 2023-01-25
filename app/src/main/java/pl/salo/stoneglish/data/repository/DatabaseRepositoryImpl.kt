package pl.salo.stoneglish.data.repository

import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.model.card.TestForCards
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import pl.salo.stoneglish.domain.services.DatabaseService
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val databaseService: DatabaseService
): DatabaseRepository {
    override suspend fun getUserFlowable(id: String) = databaseService.getUser(id)
    override suspend fun writeUserData(user: User) =
        databaseService.writeUserData(user)

    override suspend fun readCardsList(moduleName: String, userId: String) =
        databaseService.listOfCards(moduleName, userId)
    override suspend fun readModulesList(userId: String): List<String> =
        databaseService.listOfModules(userId)
    override suspend fun readTestsList(moduleName: String, userId: String): List<TestForCards> =
        databaseService.listOfTests(moduleName, userId)
    override suspend fun writeUserCard(card: Card, module: String, userId: String) =
        databaseService.writeUserCard(card, module, userId)

    override suspend fun readListOfDailyCards(): List<Card> =
        databaseService.getListOfDailyCards()

    override suspend fun writeUserCards(cards: List<Card>, module: String, userId: String) {
        databaseService.writeUserCards(cards, module, userId)
    }

    override suspend fun writeNewTopic(topic: Topic) =
        databaseService.writeNewTopic(topic)

    override suspend fun getListOfPolishWords() = databaseService.getListOfPolishWords()

}