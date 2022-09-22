package pl.salo.stoneglish.domain.use_cases.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.repository.SignUpDataRepository
import javax.inject.Inject

class SignUpDataSetEnglishLevelUseCase @Inject constructor(val signUpDataRepository: SignUpDataRepository) {
    operator fun invoke(e_level: String): Flow<Resource<Unit>> = flow {
        try {
            signUpDataRepository.setEnglishLevel(e_level)
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message))
        }
    }
}