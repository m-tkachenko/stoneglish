package pl.salo.stoneglish.domain.use_cases.topic

import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.data.model.home.TopicByType
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import javax.inject.Inject

class ReadVerticalTopicsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(): Flow<Resource<List<TopicByType>>> = flow {
        try {
            emit(Resource.Loading())

            val topicsByType = databaseRepository.readVerticalTopics()

            emit(Resource.Success(data = topicsByType))
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