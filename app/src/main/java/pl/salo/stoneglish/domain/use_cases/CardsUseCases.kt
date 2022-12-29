package pl.salo.stoneglish.domain.use_cases

import pl.salo.stoneglish.domain.use_cases.cards.*

data class CardsUseCases(
    val addNewCard: WriteUserCardUseCase,
    val cardsList: ReadListOfCardsUseCase,
    val modulesList: ReadListOfModulesUseCase,
    val testsList: ReadListOfTestsUseCase,
    val writeCardsUseCase: WriteUserCardsUseCase
)
