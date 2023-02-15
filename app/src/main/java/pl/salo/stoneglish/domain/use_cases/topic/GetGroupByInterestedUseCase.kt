package pl.salo.stoneglish.domain.use_cases.topic

import pl.salo.stoneglish.data.model.home.HorizontalGroup
import pl.salo.stoneglish.data.model.home.HorizontalGroupByType
import pl.salo.stoneglish.data.model.home.TopicType

class GetGroupByInterestedUseCase {
    operator fun invoke(
        interestedTopicTypes: List<TopicType>,
        horizontalGroups: List<HorizontalGroupByType>
    ): HorizontalGroup {
        val groups = horizontalGroups.find {
            it.horizontalGroupType == interestedTopicTypes.random()
        }?.horizontalGroups

        return groups?.random() ?: HorizontalGroup(
            horizontalGroupTitle = "No topics"
        )
    }
}