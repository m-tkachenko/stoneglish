package pl.salo.stoneglish.domain.use_cases

import pl.salo.stoneglish.domain.use_cases.database.GetCurrentUserUseCase
import pl.salo.stoneglish.domain.use_cases.database.WriteUserDataUseCase

data class DatabaseUseCases(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val writeUserDataUseCase: WriteUserDataUseCase,
)