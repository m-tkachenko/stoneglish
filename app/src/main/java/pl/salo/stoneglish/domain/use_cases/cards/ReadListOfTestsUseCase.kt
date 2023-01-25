package pl.salo.stoneglish.domain.use_cases.cards

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.model.card.TestForCards
import pl.salo.stoneglish.domain.model.card.TestType
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import retrofit2.HttpException
import javax.inject.Inject

class ReadListOfTestsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Resource<List<TestForCards>>> = flow {
        try {
            emit(Resource.Loading())

            val listOfTests = listOf(
                TestForCards(
                    TestType.MEMORIZATION,
                    "Practice until you learn all the words"
                )
            )

            emit(Resource.Success(data = listOfTests))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.localizedMessage
                )
            )
        }
    }
}