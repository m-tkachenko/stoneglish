package pl.salo.stoneglish.domain.repository

import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.model.card.TestForCards

interface DatabaseRepository {
    suspend fun getUserFlowable(id: String): User
    suspend fun writeUserData(user: User)

    suspend fun writeUserCard(card: Card, module: String, userId: String)
    suspend fun writeUserCards(cards: List<Card>, module: String, userId: String)

    suspend fun writeNewTopic(topic: Topic)
    suspend fun readVerticalTopics(topicType: TopicType): List<Topic>
    suspend fun readVerticalTopicByTitle(topicType: String, title: String): Topic?

    suspend fun readCardsList(moduleName: String, userId: String): List<Card>
    suspend fun readModulesList(userId: String): List<String>
    suspend fun readTestsList(moduleName: String, userId: String): List<TestForCards>

    suspend fun readListOfDailyCards(): List<Card>
}