package pl.salo.stoneglish.domain.use_cases

import pl.salo.stoneglish.domain.use_cases.topic.*

data class TopicUseCases(
    val writeNewTopic: WriteNewTopicUseCase,

    val readVerticalTopics: ReadVerticalTopicsUseCase,
    val readHorizontalGroups: ReadHorizontalGroupsUseCase,

    val getGroupByInterested: GetGroupByInterestedUseCase,
    val getGroupByType: GetGroupByTypeUseCase,

    val getTopicsAllType: GetTopicsAllTypesUseCase,
    val getTopicsByType: GetTopicsByTypeUseCase,

    val getTopicByTitle: GetTopicByTitleUseCase
)
