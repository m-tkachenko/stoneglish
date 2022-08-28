package pl.salo.stoneglish.domain.use_cases.auth

import kotlinx.coroutines.flow.Flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import javax.inject.Inject

class UserEmailSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> =
        authRepository.signUp(email, password)
}