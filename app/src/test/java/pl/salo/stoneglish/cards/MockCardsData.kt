package pl.salo.stoneglish.cards

import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.model.card.TestForCards
import pl.salo.stoneglish.domain.model.card.TestType

class MockCardsData {
    val mockCards = listOf(
        Card(
            word = "cardOne",
            transcription = "karteczka",
            type = "noun",
            firstTranslation = "karteczka",
            secondTranslation = "uml generator",
            thirdTranslation = "kartka"
        ),
        Card(
            word = "cardTwo",
            transcription = "karteczka",
            type = "noun",
            firstTranslation = "karteczka",
            secondTranslation = "uml generator",
            thirdTranslation = "kartka"
        ),
        Card(
            word = "cardThree",
            transcription = "karteczka",
            type = "noun",
            firstTranslation = "karteczka",
            secondTranslation = "uml generator",
            thirdTranslation = "kartka"
        )
    )
    val mockModules = listOf(
        Pair(3, "Test Module One"),
        Pair(5, "Test Module Two")
    )
    val mockTests = listOf(
        TestForCards(
            TestType.MEMORIZATION,
            "Practice until you learn all the words"
        ),
        TestForCards(
            TestType.CARDS,
            "Repeat the terms and definitions"
        )
    )
}