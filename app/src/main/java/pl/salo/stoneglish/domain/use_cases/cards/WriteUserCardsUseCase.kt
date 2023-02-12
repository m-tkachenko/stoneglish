package pl.salo.stoneglish.domain.use_cases.cards

import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import javax.inject.Inject

class WriteUserCardsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(cards: List<Card>, moduleName: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val id = authRepository.getUserId()

            if (moduleName.isBlank()){
                throw Exception("Module name can't be empty")
            }

            if (id.isNullOrBlank())
                throw Exception("Can't find user")
            else {

                val items = cards.toMutableList()

                cards.forEach {
                    if(it.word.isBlank()){
                        items.remove(it)
                    }
                    val isDigitWord = it.word.any { char ->
                        char.isDigit()
                    }
                    if(isDigitWord){
                        throw Exception("Word: ${it.word} is incorrect")
                    }
                    val isDigitTranslate = it.firstTranslation.any { char ->
                        char.isDigit()
                    }
                    if(isDigitTranslate){
                        throw Exception("Word: ${it.firstTranslation} is incorrect")
                    }
                }

                if (items.size < 2) {
                    throw Exception("Add at least 2 cards")
                }

                databaseRepository.writeUserCards(
                    cards = items,
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