package pl.salo.stoneglish.domain.use_cases.cards

import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import javax.inject.Inject

class WriteUserCardUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(card: Card, moduleName: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val id = authRepository.getUserId()

            if (id.isNullOrBlank())
                emit(Resource.Error(message = "Can't find user"))
            else {
                databaseRepository.writeUserCard(
                    card = card,
                    module = moduleName,
                    userId = id
                )
                emit(Resource.Success(data = true))
            }
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