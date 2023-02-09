package pl.salo.stoneglish.domain.use_cases.topic

import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.data.model.home.TopicByType
import pl.salo.stoneglish.data.model.home.TopicType

class GetTopicsByTypeUseCase {
    operator fun invoke(
        topicsByType: List<TopicByType>,
        chosenType: TopicType
    ) : List<Topic> {
        val topics = topicsByType
            .find {
                it.topicListType == chosenType
            }
            ?.topicList

        return topics ?: listOf()
    }
}