package pl.salo.stoneglish.domain.use_cases.topic

import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import javax.inject.Inject

class ReadVerticalTopicsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(topicType: TopicType): Flow<Resource<List<Topic>>> = flow {
        try {
            emit(Resource.Loading())

            val topics = databaseRepository.readVerticalTopics(topicType)

            emit(Resource.Success(data = topics))
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