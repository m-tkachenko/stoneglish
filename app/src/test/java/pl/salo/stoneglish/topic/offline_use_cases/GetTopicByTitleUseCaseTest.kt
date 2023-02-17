package pl.salo.stoneglish.topic.offline_use_cases

import junit.framework.TestCase.assertTrue
import org.junit.Test
import pl.salo.stoneglish.MockUserData
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.domain.use_cases.topic.GetTopicByTitleUseCase
import pl.salo.stoneglish.util.Utils.same

internal class GetTopicByTitleUseCaseTest {
    private val mockedData = MockUserData()
    private val useCase = GetTopicByTitleUseCase()

    @Test
    fun `get topics by given title, passed when return Topic with given title`() {
        val expectedTopicList = mockedData.topics.mockListAllTopicsWithGroups.filter {
            it.title == "FirstHealthTopicOne"
        }
        val topicListByTitle = useCase.invoke(
            titleToFind = "FirstHealthTopicOne",
            topicsList = mockedData.topics.mockListAllTopicsWithoutGroups,
            groupList = mockedData.topics.mockListOfGroupByType
        )

        assertTrue(
            expectedTopicList same topicListByTitle
        )
    }

    @Test
    fun `get topics by given title, passed when return empty list`() {
        val expectedTopicList = listOf<Topic>()
        val topicListByTitle = useCase.invoke(
            titleToFind = "FirstHealthTopicOneUbububu",
            topicsList = mockedData.topics.mockListAllTopicsWithoutGroups,
            groupList = mockedData.topics.mockListOfGroupByType
        )

        assertTrue(
            expectedTopicList same topicListByTitle
        )
    }
}