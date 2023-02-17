package pl.salo.stoneglish

import pl.salo.stoneglish.cards.MockCardsData
import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.data.model.home.EngLevel
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.topic.MockTopicData

class MockUserData {
    val user: User
        get() {
            val mockingUser = User()

            with(mockingUser) {
                id = "2002170924932"
                age = 20
                email = "mock@gmail.com"
                username = "mock"
                englishLevel = EngLevel.B1.toString()
                imageUrl = ""
                interestedTopics = listOf(
                    TopicType.Education.name,
                    TopicType.Art.name,
                    TopicType.Health.name,
                    TopicType.Books.name,
                    TopicType.Other.name,
                    TopicType.Holidays.name
                )
            }

            return mockingUser
        }
    val topics = MockTopicData()
    val cards = MockCardsData()
}