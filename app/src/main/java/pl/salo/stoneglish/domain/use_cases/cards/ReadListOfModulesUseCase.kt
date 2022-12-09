package pl.salo.stoneglish.domain.use_cases.cards

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import retrofit2.HttpException
import javax.inject.Inject

class ReadListOfModulesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(): Flow<Resource<List<String>>> = flow {
        try {
            emit(Resource.Loading())
            val listOfCards = databaseRepository.readModulesList()
            emit(Resource.Success(data = listOfCards))
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