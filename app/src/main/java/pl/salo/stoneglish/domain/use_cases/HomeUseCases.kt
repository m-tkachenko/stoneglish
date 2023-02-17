package pl.salo.stoneglish.domain.use_cases

import pl.salo.stoneglish.domain.use_cases.home.CheckAdminUserUseCase
import pl.salo.stoneglish.domain.use_cases.home.ReadListOfDailyCardsUseCase

data class HomeUseCases(
    val dailyCards: ReadListOfDailyCardsUseCase,
    val isAdmin: CheckAdminUserUseCase
)
