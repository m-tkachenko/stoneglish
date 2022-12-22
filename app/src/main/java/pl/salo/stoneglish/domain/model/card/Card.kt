package pl.salo.stoneglish.domain.model.card

data class Card(
    val word: String = "Crimea",
    val transcription: String = "|nash|",
    val type: String = "{Ukraine}",
    val firstTranslation: String = "Krym",
    val secondTranslation: String = "Tse Ukraina",
    val thirdTranslation: String = "Donbas toze"
)
