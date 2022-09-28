package pl.salo.stoneglish.domain.use_cases.auth

import android.util.Log
import kotlinx.coroutines.flow.*
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.model.SignUpData
import pl.salo.stoneglish.domain.repository.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

class UserEmailSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(signUpData: SignUpData): Flow<Resource<SignUpData>> = flow {
        try {
            authRepository.signUp(signUpData.email!!, signUpData.password!!)
            val userId = authRepository.getUserId()
            signUpData.id = userId
            emit(Resource.Success(signUpData))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    data = null,
                    message = e.localizedMessage
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    data = null,
                    message = e.localizedMessage
                )
            )
        }
    }
}