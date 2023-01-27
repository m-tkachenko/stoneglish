package pl.salo.stoneglish.domain.use_cases.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(
    ): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val id = authRepository.getUserId()
                ?: throw Exception(
                    /**constant**/
                )
            val user = databaseRepository.getUserFlowable(id)
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message))
        }
    }
}