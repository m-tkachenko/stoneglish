package pl.salo.stoneglish.domain.model.card

data class Card(
    val word: String,
    val transcription: String,
    val type: String,
    val firstTranslation: String,
    val secondTranslation: String,
    val thirdTranslation: String
)
