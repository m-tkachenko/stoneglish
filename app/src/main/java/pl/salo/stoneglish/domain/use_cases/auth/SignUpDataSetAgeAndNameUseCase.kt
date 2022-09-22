package pl.salo.stoneglish.domain.use_cases.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.repository.SignUpDataRepository
import javax.inject.Inject

class SignUpDataSetAgeAndNameUseCase @Inject constructor(val signUpDataRepository: SignUpDataRepository) {

    operator fun invoke(name: String, age: String): Flow<Resource<Unit>> = flow {
        try {
            if (name.isBlank()) throw Exception("Name field is empty")
            if (age.isBlank()) throw Exception("Age field is empty")
            if (age.toInt() > 99 || age.toInt() == 0) throw Exception("Invalid age")
            signUpDataRepository.setAgeAndName(name, age.toInt())

            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message))
        }
    }
}