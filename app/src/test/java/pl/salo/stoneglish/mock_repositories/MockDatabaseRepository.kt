package pl.salo.stoneglish.mock_repositories

import pl.salo.stoneglish.MockUserData
import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.data.model.home.HorizontalGroupByType
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.data.model.home.TopicByType
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.model.card.TestForCards
import pl.salo.stoneglish.domain.repository.DatabaseRepository

class MockDatabaseRepository: DatabaseRepository {
    private val mockData = MockUserData()

    override suspend fun readVerticalTopics(): List<TopicByType> =
        mockData.topics.mockListOfTopicByType
    override suspend fun readHorizontalGroups(): List<HorizontalGroupByType> =
        mockData.topics.mockListOfGroupByType

    override suspend fun readListOfDailyCards(): List<Card> =
        mockData.cards.mockCards
    override suspend fun readCardsList(moduleName: String, userId: String): List<Card> =
        mockData.cards.mockCards
    override suspend fun readModulesList(userId: String): List<Pair<Int, String>> =
        mockData.cards.mockModules
    override suspend fun readTestsList(moduleName: String, userId: String): List<TestForCards> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserFlowable(id: String): User {
        TODO("Not yet implemented")
    }
    override suspend fun getListOfPolishWords(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun writeUserCategories(userId: String, categories: List<String>) {}
    override suspend fun changeUserField(userId: String, field: String, newValue: String) {}
    override suspend fun writeNewTopic(topic: Topic) {}
    override suspend fun writeUserData(user: User) {}
    override suspend fun writeUserCard(card: Card, module: String, userId: String) {}
    override suspend fun writeUserCards(cards: List<Card>, module: String, userId: String) {}
}