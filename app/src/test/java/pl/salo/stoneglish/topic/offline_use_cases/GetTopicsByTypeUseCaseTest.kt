package pl.salo.stoneglish.topic.offline_use_cases

import junit.framework.TestCase.assertTrue
import org.junit.Test
import pl.salo.stoneglish.MockUserData
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.domain.use_cases.topic.GetTopicsByTypeUseCase
import pl.salo.stoneglish.util.Utils.same

internal class GetTopicsByTypeUseCaseTest {
    private val mockedData = MockUserData()
    private val useCase = GetTopicsByTypeUseCase()

    @Test
    fun `get topics by given type, passed when topic type of list is Art`() {
        val expectedTopicList = mockedData.topics.mockListOfTopicArtType
        val topicListByType = useCase.invoke(
            topicsByType = mockedData.topics.mockListOfTopicByType,
            chosenType = TopicType.Art
        )

        assertTrue(
            expectedTopicList same topicListByType
        )
    }

    @Test
    fun `get topics by given type, passed when list is empty`() {
        val expectedTopicList = listOf<Topic>()
        val topicListByType = useCase.invoke(
            topicsByType = mockedData.topics.mockListOfTopicByType,
            chosenType = TopicType.Education
        )

        assertTrue(
            expectedTopicList same topicListByType
        )
    }
}