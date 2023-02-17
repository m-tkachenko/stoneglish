package pl.salo.stoneglish.topic.offline_use_cases

import junit.framework.TestCase.assertTrue
import org.junit.Test
import pl.salo.stoneglish.MockUserData
import pl.salo.stoneglish.data.model.home.HorizontalGroup
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.domain.use_cases.topic.GetGroupByInterestedUseCase
import pl.salo.stoneglish.util.Utils.same

internal class GetGroupByInterestedUseCaseTest {
    private val mockedData = MockUserData()
    private val useCase = GetGroupByInterestedUseCase()

    @Test
    fun `get group by given interested typs, passed when group is equal expect`() {
        val firstExpectedGroup = mockedData.topics.mockListOfGroupsEducationType.first()
        val secondExpectedGroup = mockedData.topics.mockListOfGroupsEducationType.last()

        val groupByType = useCase.invoke(
            horizontalGroups = mockedData.topics.mockListOfGroupByType,
            interestedTopicTypes = listOf(
                TopicType.valueOf(mockedData.user.interestedTopics[0])
            )
        )

        assertTrue(
            (groupByType same firstExpectedGroup) or (groupByType same secondExpectedGroup)
        )
    }

    @Test
    fun `get group by given interested typs, passed when return No Topics group`() {
        val expectedGroup = HorizontalGroup(
            horizontalGroupTitle = "No topics"
        )
        val groupByType = useCase.invoke(
            horizontalGroups = mockedData.topics.mockListOfGroupByType,
            interestedTopicTypes = listOf(
                TopicType.valueOf(mockedData.user.interestedTopics[1]),
                TopicType.valueOf(mockedData.user.interestedTopics[2])
            )
        )

        assertTrue(
            groupByType same expectedGroup
        )
    }
}