package pl.salo.stoneglish.domain.use_cases.cards

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.model.card.TestForCards
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import retrofit2.HttpException
import javax.inject.Inject

class ReadListOfTestsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(moduleName: String): Flow<Resource<List<TestForCards>>> = flow {
        try {
            emit(Resource.Loading())
            val id = authRepository.getUserId()

            val listOfTests = databaseRepository.readTestsList(moduleName, id!!)

            if (listOfTests.contains(TestForCards()) || id.isEmpty())
                emit(Resource.Error(message = "We don't have any tests for you :("))
            else
                emit(Resource.Success(data = listOfTests))
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