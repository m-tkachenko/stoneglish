package pl.salo.stoneglish.domain.use_cases.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

class UserSignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Resource<Unit>> =
        flow {
            try {
                emit(Resource.Loading())
                val isSuccess = authRepository.signOut()
                if (!isSuccess) throw Exception(/**constants**/)
                emit(Resource.Success(Unit))
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