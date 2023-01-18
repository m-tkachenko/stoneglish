package pl.salo.stoneglish.presentation.core.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.salo.stoneglish.data.model.home.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.use_cases.CardsUseCases
import pl.salo.stoneglish.domain.use_cases.DatabaseUseCases
import pl.salo.stoneglish.domain.use_cases.HomeUseCases
import pl.salo.stoneglish.domain.use_cases.TopicUseCases
import pl.salo.stoneglish.util.Event
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cardDatabase: CardsUseCases,
    private val homeDatabase: HomeUseCases,
    private val databaseUseCases: DatabaseUseCases,
    private val topicDatabase: TopicUseCases
) : ViewModel() {

    // State for daily cards
    private val _modulesState = MutableLiveData<Event<Resource<List<String>>>>()
    val modulesState: LiveData<Event<Resource<List<String>>>>
        get() = _modulesState

    private val _addCardState = MutableLiveData<Event<Resource<Boolean>>>()
    val addCardState: LiveData<Event<Resource<Boolean>>>
        get() = _addCardState

    private val _dailyCardState = MutableLiveData<Event<Resource<List<Card>>>>()
    val dailyCardState: LiveData<Event<Resource<List<Card>>>>
        get() = _dailyCardState

    // State for favourite topics chips
    private val _currentUser = MutableLiveData<Resource<User>>()
    val currentUser: LiveData<Resource<User>>
        get() = _currentUser

    // State for topics
    private val _selectedTopic = MutableLiveData<Topic>()
    val selectedTopic: LiveData<Topic>
        get() = _selectedTopic

    private val _verticalTopics = MutableLiveData<Event<Resource<List<Topic>>>>()
    val verticalTopics: LiveData<Event<Resource<List<Topic>>>>
        get() = _verticalTopics

    var isNotPreview = true
    init {
        getCurrentUser()
    }

    // Functions for daily card
    fun downloadDailyCards() {
        homeDatabase.dailyCards().onEach { dailyCards ->
            _dailyCardState.postValue(Event(dailyCards))
        }.launchIn(viewModelScope)
    }

    fun downloadModules() {
        cardDatabase.modulesList().onEach { modules ->
            _modulesState.postValue(Event(modules))
        }.launchIn(viewModelScope)
    }

    fun addNewCard(newCard: Card, moduleName: String) {
        cardDatabase.addNewCard(
            card = newCard,
            moduleName = moduleName
        ).onEach { status ->
            _addCardState.postValue(Event(status))
        }.launchIn(viewModelScope)
    }

    // Functions for favourite topics chips
    fun getCurrentUser() {
        databaseUseCases.getCurrentUserUseCase().onEach { user ->
            _currentUser.postValue(user)
        }.launchIn(viewModelScope)
    }

    // Functions for favourite topics chips
    fun selectTopic() {
        val listeningSpeaking = listOf(
            ListeningSpeaking("This text is example", "Ten tekst jest przykładem"),
            ListeningSpeaking("But this is no an example", "Ale to nie jest przykład"),
            ListeningSpeaking("A little bit more examples", "Trochę więcej przykładów"),
        )
        val keywords = listOf(
            Keyword("Cat", "/ket/", "kot"),
            Keyword("Dog", "/dog woniuczyj/", "pies"),
            Keyword("Example", "/elpmaxe/", "przykład"),
            Keyword("Stoneglish", "/very cool/", "najlepsza appka")
        )
        val similarTopics = listOf(
            SimilarTopic("https://pixlr.com/images/index/remove-bg.webp", "Wow"),
            SimilarTopic(
                "https://images.unsplash.com/photo-1501183007986-d0d080b147f9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8ZnJlZXxlbnwwfHwwfHw%3D&w=1000&q=80",
                "OMG"
            ),
            SimilarTopic(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQsJSFvewBphCd0-gaP5jDukdNiNsEaaiOnYA&usqp=CAU",
                "WTF"
            ),
        )

        val t = Topic(
            title = "Example topic",
            eng_level = listOf(EngLevel.A2),
            type = listOf(TopicType.Films),
            imgUrl = "data:image/jpeg;base64,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",
            text = "Here you can find activities to practise your reading skills. Reading will help you to improve your understanding of the language and build your vocabulary.\n" +
                    "\n" +
                    "The self-study lessons in this section are written and organised by English level based on the Common European Framework of Reference for languages (CEFR). There are different types of texts and interactive exercises that practise the reading skills you need to do well in your studies, to get ahead at work and to communicate in English in your free time.\n" +
                    "\n" +
                    "Take our free online English test to find out which level to choose. Select your level, from A1 English level (elementary) to C1 English level (advanced), and improve your reading skills at your own speed, whenever it's convenient for you.\n" +
                    "\n",
            exercises = null,
            listeningAndSpeaking = listeningSpeaking,
            keywords = keywords,
            similarTopics = similarTopics,
            horizontalGroupTitle = null
        )
        _selectedTopic.value = t
    }

    fun setTopicToPreview(newPreviewTopic: Topic) {
        _selectedTopic.postValue(newPreviewTopic)
        isNotPreview = false
    }

    fun getTopic() =
        selectedTopic.value

    fun readVerticalTopics(topicType: TopicType) {
        topicDatabase.readVerticalTopics(
            topicType = topicType
        ).onEach { topics ->
            _verticalTopics.postValue(Event(topics))
        }.launchIn(viewModelScope)
    }
}