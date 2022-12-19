package pl.salo.stoneglish.domain.use_cases.dictionary

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.dictionary.Word
import pl.salo.stoneglish.domain.repository.DictionaryRepository
import pl.salo.stoneglish.util.Constants
import javax.inject.Inject

class DictionaryGetWordDataUseCase @Inject constructor(private val repository: DictionaryRepository) {
    operator fun invoke(query: String): Flow<Resource<Word>> = flow {
        try {
            val result = repository.getWordData(query)

            if (!result.isSuccessful) throw Exception(Constants.SOMETHING_WENT_WRONG)

            val word = result.body()?.first()
            emit(Resource.Success(word))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message))
        }
    }
}