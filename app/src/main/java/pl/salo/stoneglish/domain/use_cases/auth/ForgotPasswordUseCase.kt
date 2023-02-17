package pl.salo.stoneglish.domain.use_cases.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.util.isValidEmail
import retrofit2.HttpException
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())

            val isEmailExist = authRepository.isEmailExist(email)

            if (!isEmailExist) throw Exception("No such email")
            if (!isValidEmail(email)) throw Exception("Email is not valid")

            authRepository.forgotPassword(email)
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