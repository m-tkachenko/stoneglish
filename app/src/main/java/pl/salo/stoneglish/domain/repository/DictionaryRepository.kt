package pl.salo.stoneglish.domain.repository

import pl.salo.stoneglish.data.model.dictionary.DictionaryJson
import retrofit2.Response

interface DictionaryRepository {
    suspend fun getWordData(word: String): Response<DictionaryJson>
}