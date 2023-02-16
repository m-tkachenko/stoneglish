package pl.salo.stoneglish.topic.offline_use_cases

import junit.framework.TestCase.*
import org.junit.Test
import pl.salo.stoneglish.MockUserData
import pl.salo.stoneglish.domain.use_cases.topic.GetTopicsAllTypesUseCase
import pl.salo.stoneglish.util.Utils.same

internal class GetTopicsAllTypesUseCaseTest {
    private val mockedData = MockUserData()
    private val useCase = GetTopicsAllTypesUseCase()

    @Test
    fun `get all topics, passed when return expected topic list`() {
        val expectedTopicList = mockedData.topics.mockListAllTopicsWithoutGroups
        val topicListByType = useCase.invoke(
            listOfTypedTopics = mockedData.topics.mockListOfTopicByType
        )

        assertTrue(
            expectedTopicList same topicListByType
        )
    }

    @Test
    fun `get all topics, passed when return not expected topic list`() {
        val expectedTopicList = mockedData.topics.mockListOfTopicArtType
        val topicListByType = useCase.invoke(
            listOfTypedTopics = mockedData.topics.mockListOfTopicByType
        )

        assertFalse(
            expectedTopicList same topicListByType
        )
    }
}