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
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.domain.use_cases.CardsUseCases
import pl.salo.stoneglish.domain.use_cases.DatabaseUseCases
import pl.salo.stoneglish.domain.use_cases.HomeUseCases
import pl.salo.stoneglish.domain.use_cases.TopicUseCases
import pl.salo.stoneglish.util.Event
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicDatabase: TopicUseCases
) : ViewModel() {
    private val _topic = MutableLiveData<Event<Resource<Topic>>>()
    val topic: LiveData<Event<Resource<Topic>>>
        get() = _topic
}