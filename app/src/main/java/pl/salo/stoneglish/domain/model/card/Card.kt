package pl.salo.stoneglish.domain.model.card

data class Card(
    var word: String = "",
    val transcription: String = "",
    val type: String = "",
    var firstTranslation: String = "",
    val secondTranslation: String = "",
    val thirdTranslation: String = ""
)
