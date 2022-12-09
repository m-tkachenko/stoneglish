package pl.salo.stoneglish.domain.use_cases

import pl.salo.stoneglish.domain.use_cases.cards.ReadListOfCardsUseCase
import pl.salo.stoneglish.domain.use_cases.cards.ReadListOfModulesUseCase
import pl.salo.stoneglish.domain.use_cases.cards.ReadListOfTestsUseCase
import pl.salo.stoneglish.domain.use_cases.cards.WriteUserCardUseCase

data class CardsUseCases(
    val addNewCard: WriteUserCardUseCase,
    val cardsList: ReadListOfCardsUseCase,
    val modulesList: ReadListOfModulesUseCase,
    val testsList: ReadListOfTestsUseCase
)
