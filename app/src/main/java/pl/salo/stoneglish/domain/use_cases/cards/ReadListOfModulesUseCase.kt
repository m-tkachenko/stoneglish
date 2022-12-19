package pl.salo.stoneglish.domain.use_cases.cards

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import retrofit2.HttpException
import javax.inject.Inject

class ReadListOfModulesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Resource<List<String>>> = flow {
        try {
            emit(Resource.Loading())
            val id = authRepository.getUserId()

            if (id.isNullOrBlank())
                emit(Resource.Error(message = "Can't find user"))
            else
                emit(Resource.Success(data = databaseRepository.readModulesList(id)))

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                message = e.localizedMessage
            ))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                message = e.localizedMessage
            ))
        }
    }
}