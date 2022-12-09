package pl.salo.stoneglish.domain.use_cases.cards

import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import javax.inject.Inject

class WriteUserCardUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(card: Card, moduleName: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            databaseRepository.writeUserCard(
                card = card,
                module = moduleName
            )
            emit(Resource.Success(data = true))
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