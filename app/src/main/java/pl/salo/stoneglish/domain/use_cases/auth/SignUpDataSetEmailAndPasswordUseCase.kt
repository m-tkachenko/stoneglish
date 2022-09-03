package pl.salo.stoneglish.domain.use_cases.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.repository.SignUpDataRepository
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.util.isValidEmail
import pl.salo.stoneglish.util.isValidPassword
import javax.inject.Inject

class SignUpDataSetEmailAndPasswordUseCase @Inject constructor(
    val signUpDataRepository: SignUpDataRepository,
    val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val isEmailExist = authRepository.isEmailExist(email)

            if (isEmailExist) throw Exception("Such email exist")
            if (!isValidEmail(email)) throw Exception("Email is not valid")
            if (!isValidPassword(password)) throw Exception("Password is not valid")

            signUpDataRepository.setEmailAndPassword(email, password)

            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message))
        }
    }

}
