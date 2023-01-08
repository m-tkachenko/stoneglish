package pl.salo.stoneglish.data.model.home

data class Topic(
    val title: String,
    val horizontalGroupTitle: String?,
    val eng_level: List<EngLevel>,
    val type: List<TopicType>,
    val imgUrl: String,
    val text: String,
    val exercises: TopicTest?,
    val listeningAndSpeaking: List<ListeningSpeaking>,
    val keywords: List<Keyword>,
    val similarTopics: List<SimilarTopic>?
)

enum class EngLevel {
    A0, A1, A2, B1, B2, C1, C2
}

enum class TopicType(val type: String) {
    Art("art"),
    Books("books"),
    Celebrities("celebrities"),
    Countries("countries"),
    Educations("educations"),
    Films("films"),
    Holidays("holidays"),
    Lifehack("lifehack"),
    Places("places"),
    Shopping("shopping"),
    Sport("sport"),
    Traveling("traveling"),
    Work("work"),
    Other("other")
}
