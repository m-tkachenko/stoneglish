package pl.salo.stoneglish.domain.use_cases.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

class UserEmailSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            authRepository.emailSignIn(email, password)
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