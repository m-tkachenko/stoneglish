package pl.salo.stoneglish.data.model

data class User(
    var id: String,
    var email: String,
    var username: String,
    var age: Int,
    var interestedTopics: List<String>,
    var englishLevel: String,
    var imageUrl: String?
) {
    constructor() : this("", "", "", 0, emptyList(), "", null)
}