package pl.salo.stoneglish.domain.use_cases.auth

import com.facebook.AccessToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import javax.inject.Inject

class UserSignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            authRepository.signOut()

            emit(Resource.Success(data = true))
        }
}