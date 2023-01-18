package pl.salo.stoneglish.domain.use_cases

import pl.salo.stoneglish.domain.use_cases.topic.ReadVerticalTopicByTitleUseCase
import pl.salo.stoneglish.domain.use_cases.topic.ReadVerticalTopicsUseCase
import pl.salo.stoneglish.domain.use_cases.topic.WriteNewTopicUseCase

data class TopicUseCases(
    val writeNewTopic: WriteNewTopicUseCase,
    val readVerticalTopics: ReadVerticalTopicsUseCase,
    val readVerticalTopicByTitle: ReadVerticalTopicByTitleUseCase
)
