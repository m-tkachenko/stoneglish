package pl.salo.stoneglish.domain.use_cases.topic

import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import javax.inject.Inject

class WriteNewTopicUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(topic: Topic): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())

            databaseRepository.writeNewTopic(topic)

            emit(Resource.Success(data = true))
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