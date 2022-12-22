package pl.salo.stoneglish.domain.use_cases.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.repository.SignUpDataRepository
import pl.salo.stoneglish.domain.model.auth.SignUpCategoryItem
import javax.inject.Inject

class SignUpDataSetCategoryState @Inject constructor(val signUpDataRepository: SignUpDataRepository) {

    operator fun invoke(category: SignUpCategoryItem): Flow<Resource<Unit>> = flow {
        try {
            signUpDataRepository.setCategoryState(category)
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message))
        }
    }
}