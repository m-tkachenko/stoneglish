package pl.salo.stoneglish.presentation.core.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.*
import pl.salo.stoneglish.domain.use_cases.DatabaseUseCases
import pl.salo.stoneglish.util.Event
import javax.inject.Inject

@HiltViewModel
class AddTopicViewModel @Inject constructor(
    private val database: DatabaseUseCases
) : ViewModel() {
    private val _newTopicUploadState = MutableLiveData<Event<Resource<Boolean>>>()
    val newTopicUploadState: LiveData<Event<Resource<Boolean>>>
        get() = _newTopicUploadState

    fun addNewTopic(topic: Topic) {
        database.writeNewTopic(topic).onEach { status ->
            _newTopicUploadState.postValue(Event(status))
        }.launchIn(viewModelScope)
    }


    val exampleTopic =
        Topic(
            title = "Uhuu",
            imgUrl = "https://images.unsplash.com/photo-1501183007986-d0d080b147f9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8ZnJlZXxlbnwwfHwwfHw%3D&w=1000&q=80",
            text = "New example topic",
            type = listOf(TopicType.Art, TopicType.Books, TopicType.Other),
            eng_level = listOf(EngLevel.A0, EngLevel.A1),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Yes", translatedText = "Tak"),
                ListeningSpeaking(text = "No", translatedText = "Nie")
            ),
            keywords = listOf(
                Keyword(word = "Topic", translate = "Temat", phonetic = ""),
                Keyword(word = "New", translate = "Nowy", phonetic = ""),
                Keyword(word = "Example", translate = "Przykład", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = "", title = "Similar"),
                SimilarTopic(imgUrl = "", title = "Second similar")
            ),
            horizontalGroupTitle = null
        )
}