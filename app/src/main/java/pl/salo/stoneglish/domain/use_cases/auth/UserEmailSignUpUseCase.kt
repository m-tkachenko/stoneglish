package pl.salo.stoneglish.domain.use_cases.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import javax.inject.Inject

class UserEmailSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            val response = authRepository.signUp(email, password)

            emit(
                if (response.data == true)
                    Resource.Success(
                        data = response.data
                    )
                else
                    Resource.Error(
                        data = response.data,
                        message = response.message
                    )
            )
        }
}