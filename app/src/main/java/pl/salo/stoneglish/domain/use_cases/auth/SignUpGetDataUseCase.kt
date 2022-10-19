package pl.salo.stoneglish.domain.use_cases.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.repository.SignUpDataRepository
import pl.salo.stoneglish.domain.model.SignUpData
import javax.inject.Inject

class SignUpGetDataUseCase @Inject constructor(val signUpDataRepository: SignUpDataRepository) {
    operator fun invoke(): Flow<Resource<SignUpData>> = flow {
        try {
            val data = signUpDataRepository.getSignUpData()
            if (!data.isNotEmpty()) throw Exception("Something went wrong")
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message))
        }
    }
}