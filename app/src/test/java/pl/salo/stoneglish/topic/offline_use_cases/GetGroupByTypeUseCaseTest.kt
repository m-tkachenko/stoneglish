package pl.salo.stoneglish.topic.offline_use_cases

import junit.framework.TestCase.assertTrue
import org.junit.Test
import pl.salo.stoneglish.MockUserData
import pl.salo.stoneglish.data.model.home.HorizontalGroup
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.domain.use_cases.topic.GetGroupByTypeUseCase
import pl.salo.stoneglish.topic.MockTopicData
import pl.salo.stoneglish.util.Utils.same

internal class GetGroupByTypeUseCaseTest {
    private val mockedData = MockUserData()
    private val useCase = GetGroupByTypeUseCase()

    @Test
    fun `get group by given type, passed when group type is Education`() {
        val firstExpectedGroup = mockedData.topics.mockListOfGroupsEducationType.first()
        val secondExpectedGroup = mockedData.topics.mockListOfGroupsEducationType.last()

        val groupByType = useCase.invoke(
            horizontalGroupByType = mockedData.topics.mockListOfGroupByType,
            chosenType = TopicType.Education
        )

        assertTrue(
            (groupByType same firstExpectedGroup) or (groupByType same secondExpectedGroup)
        )
    }

    @Test
    fun `get group by given type, passed when group is No Topics`() {
        val expectedGroup = HorizontalGroup(horizontalGroupTitle = "No topics")
        val groupByType = useCase.invoke(
            horizontalGroupByType = mockedData.topics.mockListOfGroupByType,
            chosenType = TopicType.Art
        )

        assertTrue(
            expectedGroup same groupByType
        )
    }
}