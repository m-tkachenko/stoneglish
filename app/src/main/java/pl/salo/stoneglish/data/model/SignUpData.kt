package pl.salo.stoneglish.data.model

data class SignUpData(
    var email: String? = null,
    var password: String? = null,
    var username: String? = null,
    var age: Int? = null,
    var interestedTopics: List<String>? = null,
    var englishLevel: String? = null
)
