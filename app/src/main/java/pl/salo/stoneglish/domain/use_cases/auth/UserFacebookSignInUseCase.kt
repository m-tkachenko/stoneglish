package pl.salo.stoneglish.domain.use_cases.auth

import com.facebook.AccessToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

class UserFacebookSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(token: AccessToken): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            authRepository.facebookSignIn(token)
            emit(Resource.Success(data = true))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                data = false,
                message = e.localizedMessage
            ))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                data = false,
                message = e.localizedMessage
            ))
        }
    }
}