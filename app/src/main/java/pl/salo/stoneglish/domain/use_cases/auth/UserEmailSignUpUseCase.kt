package pl.salo.stoneglish.domain.use_cases.auth

import kotlinx.coroutines.flow.*
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

class UserEmailSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            authRepository.signUp(email, password)
            emit(Resource.Success(data = true))
        } catch (e: HttpException) {
            emit(Resource.Error(
                data = false,
                message = e.localizedMessage
            ))
        } catch (e: Exception) {
            emit(Resource.Error(
                data = false,
                message = e.localizedMessage
            ))
        }
    }
}