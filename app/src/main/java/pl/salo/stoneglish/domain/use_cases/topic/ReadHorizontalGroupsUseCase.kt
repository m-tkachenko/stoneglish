package pl.salo.stoneglish.domain.use_cases.topic

import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.HorizontalGroupByType
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import javax.inject.Inject

class ReadHorizontalGroupsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(): Flow<Resource<List<HorizontalGroupByType>>> = flow {
        try {
            emit(Resource.Loading())

            val horizontalGroupListByType = databaseRepository.readHorizontalGroups()

            emit(Resource.Success(data = horizontalGroupListByType))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = e.localizedMessage
            ))
        } catch (e: Exception) {
            emit(Resource.Error(
                message = e.localizedMessage
            ))
        }
    }
}