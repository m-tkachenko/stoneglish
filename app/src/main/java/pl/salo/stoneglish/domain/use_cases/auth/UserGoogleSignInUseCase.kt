package pl.salo.stoneglish.domain.use_cases.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

class UserGoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(account: GoogleSignInAccount): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            authRepository.googleSignIn(account)
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