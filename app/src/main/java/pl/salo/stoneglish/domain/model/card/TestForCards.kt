package pl.salo.stoneglish.domain.model.card

data class TestForCards(
    val name: TestType,
    val description: String,
    var icon: Int = 0,
    var backgroundImage: Int = 0
)

enum class TestType(val type: String) {
    MEMORIZATION("Memorization"),
    CARDS("Cards")
}
