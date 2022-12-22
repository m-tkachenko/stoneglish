package pl.salo.stoneglish.domain.model.auth

data class SignUpData(
    var id: String? = null,
    var email: String? = null,
    var password: String? = null,
    var username: String? = null,
    var age: Int? = null,
    var interestedTopics: MutableList<SignUpCategoryItem> = mutableListOf(),
    var englishLevel: String? = null
) {
    fun isNotEmpty(): Boolean {
        return email != null || password != null || username != null || age != null || englishLevel != null
    }
}