package pl.salo.stoneglish.domain.use_cases.topic

import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import pl.salo.stoneglish.util.Utils.isNull
import javax.inject.Inject

class ReadVerticalTopicByTitleUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(topicType: String, title: String): Flow<Resource<Topic>> = flow {
        try {
            emit(Resource.Loading())

            val topic = databaseRepository.readVerticalTopicByTitle(topicType, title)

            if(topic.isNull())
                emit(Resource.Error(
                    message = "There is no topics with this title"
                ))
            else
                emit(Resource.Success(data = topic))
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