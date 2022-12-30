package pl.salo.stoneglish.domain.use_cases.home

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import retrofit2.HttpException
import javax.inject.Inject

class ReadListOfDailyCardsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(): Flow<Resource<List<Card>>> = flow {
        try {
            emit(Resource.Loading())

            val listOfDailyCards = databaseRepository.readListOfDailyCards()

            emit(Resource.Success(
                data = listOfDailyCards
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = e.localizedMessage
            ))
        } catch (e: Exception) {
            emit(Resource.Error(
                message = e.localizedMessage
            ))
        }
    }
}