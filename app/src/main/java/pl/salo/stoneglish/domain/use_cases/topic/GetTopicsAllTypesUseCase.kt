package pl.salo.stoneglish.domain.use_cases.topic

import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.data.model.home.TopicByType

class GetTopicsAllTypesUseCase {
    operator fun invoke(
        listOfTypedTopics: List<TopicByType>
    ) : List<Topic> {
        val topicsListAllType = mutableListOf<Topic>()

        listOfTypedTopics.forEach { topicListByType ->
            topicsListAllType.addAll(topicListByType.topicList)
        }

        return topicsListAllType
    }
}