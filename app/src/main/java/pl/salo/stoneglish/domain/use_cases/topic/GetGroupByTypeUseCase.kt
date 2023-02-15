package pl.salo.stoneglish.domain.use_cases.topic

import pl.salo.stoneglish.data.model.home.HorizontalGroup
import pl.salo.stoneglish.data.model.home.HorizontalGroupByType
import pl.salo.stoneglish.data.model.home.TopicType

class GetGroupByTypeUseCase {
    operator fun invoke(
        chosenType: TopicType,
        horizontalGroupByType: List<HorizontalGroupByType>
    ): HorizontalGroup {
        return horizontalGroupByType
            .find {
                it.horizontalGroupType == chosenType
            }
            ?.horizontalGroups?.random()
            ?: HorizontalGroup(
                horizontalGroupTitle = "No topics"
            )
    }
}