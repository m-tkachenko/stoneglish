package pl.salo.stoneglish.data.model.home

data class Topic(
    val title: String = "",
    val horizontalGroupTitle: String? = null,
    val eng_level: List<EngLevel> = listOf(),
    val type: List<TopicType> = listOf(),
    val imgUrl: String = "",
    val text: String = "",
    val exercises: TopicTest? = null,
    val listeningAndSpeaking: List<ListeningSpeaking> = listOf(),
    val keywords: List<Keyword> = listOf(),
    val similarTopics: List<SimilarTopic>? = null
)

enum class EngLevel {
    A0, A1, A2, B1, B2, C1, C2
}

enum class TopicType(val type: String = "") {
    Art("art"),
    Books("books"),
    Celebrities("celebrities"),
    Countries("countries"),
    Education("education"),
    Films("films"),
    Holidays("holidays"),
    Lifehack("lifehack"),
    Places("places"),
    Shopping("shopping"),
    Sport("sport"),
    Traveling("traveling"),
    Work("work"),
    Health("health"),
    Other("other")
}
