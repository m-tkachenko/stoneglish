package pl.salo.stoneglish.data.repository

import pl.salo.stoneglish.data.retrofit.DictionaryApiService
import pl.salo.stoneglish.domain.repository.DictionaryRepository
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(private val dictionary: DictionaryApiService): DictionaryRepository {
    override suspend fun getWordData(word: String) = dictionary.getWordData(word)
}