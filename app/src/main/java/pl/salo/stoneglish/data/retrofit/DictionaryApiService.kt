package pl.salo.stoneglish.data.retrofit

import pl.salo.stoneglish.data.model.dictionary.DictionaryJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {

    @GET("{word}")
    suspend fun getWordData(@Path("word") word: String): Response<DictionaryJson>
}