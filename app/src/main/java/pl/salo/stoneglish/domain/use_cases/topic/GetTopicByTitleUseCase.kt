package pl.salo.stoneglish.domain.use_cases.topic

import pl.salo.stoneglish.data.model.home.HorizontalGroupByType
import pl.salo.stoneglish.data.model.home.Topic

class GetTopicByTitleUseCase {
    operator fun invoke(
        titleToFind: String,
        topicsList: List<Topic>,
        groupList: List<HorizontalGroupByType>
    ): List<Topic> {
        val allTopicsList = mutableListOf<Topic>()

        allTopicsList.addAll(topicsList)
        groupList.forEach {
            it.horizontalGroups.forEach { group ->
                allTopicsList.addAll(group.topics)
            }
        }

        return allTopicsList.filter {
            it.title == titleToFind
        }
    }
}