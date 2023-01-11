package pl.salo.stoneglish.domain.use_cases.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import pl.salo.stoneglish.presentation.core.profile.Field
import javax.inject.Inject

class UpdateUserFieldUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(
        userId: String,
        field: Field,
        newValue: String
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val fieldName = when(field){
                Field.NAME -> {"username"}
                Field.AGE -> {"age"}
            }
            databaseRepository.changeUserField(userId, fieldName, newValue)
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message))
        }
    }
}