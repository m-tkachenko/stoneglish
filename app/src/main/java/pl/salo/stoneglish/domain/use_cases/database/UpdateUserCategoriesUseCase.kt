package pl.salo.stoneglish.domain.use_cases.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import javax.inject.Inject

class UpdateUserCategoriesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(
        userId: String,
        categories: List<String>
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            databaseRepository.writeUserCategories(userId, categories)
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message))
        }
    }
}