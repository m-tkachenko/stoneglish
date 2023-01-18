package pl.salo.stoneglish.domain.model.card

data class TestForCards(
    val name: TestType,
    val description: String
)

enum class TestType(val type: String){
    MEMORIZATION("Memorization")
}
