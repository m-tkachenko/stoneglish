package pl.salo.stoneglish.data.model

data class User (
    var id: String,
    var email: String,
    var username: String,
    var age: Int,
    var interestedTopics: List<String>,
    var englishLevel: String
)