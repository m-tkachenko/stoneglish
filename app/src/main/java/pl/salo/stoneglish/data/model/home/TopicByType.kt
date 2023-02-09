package pl.salo.stoneglish.data.model.home

data class TopicByType(
    val topicListType: TopicType,
    val topicList: List<Topic>
)
