package pl.salo.stoneglish.presentation.core.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.domain.use_cases.TopicUseCases
import pl.salo.stoneglish.util.Event
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicDatabase: TopicUseCases
) : ViewModel() {
    private val _newTopicUploadState = MutableLiveData<Event<Resource<Boolean>>>()
    val newTopicUploadState: LiveData<Event<Resource<Boolean>>>
        get() = _newTopicUploadState

    fun addNewTopic(topic: Topic) {
        topicDatabase.writeNewTopic(topic).onEach { status ->
            _newTopicUploadState.postValue(Event(status))
        }.launchIn(viewModelScope)
    }

    private val _topic = MutableLiveData<Topic>()
    val topicForShow
        get() = _topic.value ?: Topic()

    private val _topicsList = MutableLiveData<List<Topic>>()
    val topicsListFowShow
        get() = _topicsList.value ?: listOf()

    var isNotPreview = true

    fun setTopic(
        topicToShow: Topic,
        isPreviewTopic: Boolean = false
    ) {
        _topic.postValue(topicToShow)
        isNotPreview = !isPreviewTopic
    }

    fun setTopicList(topicsList: List<Topic>) {
        _topicsList.postValue(topicsList)
    }
}