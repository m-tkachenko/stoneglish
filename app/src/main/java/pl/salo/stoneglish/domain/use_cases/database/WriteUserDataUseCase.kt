package pl.salo.stoneglish.domain.use_cases.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.model.SignUpData
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import pl.salo.stoneglish.util.DataMapper
import javax.inject.Inject

class WriteUserDataUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val dataMapper: DataMapper
) {
    suspend operator fun invoke(
        user: User? = null,
        signUpData: SignUpData? = null
    ): Flow<Resource<Unit>> = flow {
        try {
            if (user == null && signUpData == null) throw Exception("Something went wrong :(")
            val userData = user ?: signUpData
            if (userData is SignUpData) {
                databaseRepository.writeUserData(dataMapper.signUpDataToUser(userData))
            } else {
                databaseRepository.writeUserData(userData as User)
            }
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message))
        }
    }
}