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
    val topicToShow
        get() = _selectedTopic.value ?: Topic()

    private val _downloadedTopicsList = MutableLiveData<List<Topic>>()
    val listTopicsToShow
        get() = _downloadedTopicsList.value ?: listOf()

    private val _verticalTopics = MutableLiveData<Event<Resource<List<TopicByType>>>>()
    val verticalTopics: LiveData<Event<Resource<List<TopicByType>>>>
        get() = _verticalTopics
    private var topicsByType = listOf<TopicByType>()
    private var topicsAllTypes = listOf<Topic>()

    private val _horizontalGroups = MutableLiveData<Event<Resource<List<HorizontalGroupByType>>>>()
    val horizontalGroups: LiveData<Event<Resource<List<HorizontalGroupByType>>>>
        get() = _horizontalGroups
    private var horizontalGroupByType = listOf<HorizontalGroupByType>()
    private var interestedHorizontalGroup = HorizontalGroup()

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

    // Functions for topics
    fun setTopic(
        topicToShow: Topic,
        isPreviewTopic: Boolean = false
    ) {
        _selectedTopic.postValue(topicToShow)
        isNotPreview = !isPreviewTopic
    }

    fun setTopicList(topicsList: List<Topic>) {
        _downloadedTopicsList.postValue(topicsList)
    }

    fun readVerticalTopics() {
        topicDatabase.readVerticalTopics().onEach { topic ->
            _verticalTopics.postValue(Event(topic))
        }.launchIn(viewModelScope)
    }
    fun getVerticalTopicsAllTypes(listOfTypedTopics: List<TopicByType>): List<Topic> {
        val topicsListAllType = topicDatabase
            .getTopicsAllType(listOfTypedTopics)

        topicsAllTypes = topicsListAllType
        topicsByType = listOfTypedTopics

        return topicsListAllType
    }
    fun getTopicsByChosenType(chosenType: TopicType): List<Topic> {
        return topicDatabase.getTopicsByType(
            topicsByType,
            chosenType
        )
    }
    fun getTopicsAllTypes() = topicsAllTypes

    fun readHorizontalGroups() {
        topicDatabase.readHorizontalGroups().onEach { group ->
            _horizontalGroups.postValue(Event(group))
        }.launchIn(viewModelScope)
    }
    fun getInterestedHorizontalGroup(
        interestedTopicTypes: List<TopicType>,
        horizontalGroups: List<HorizontalGroupByType>
    ): HorizontalGroup {
        val interestedGroup = topicDatabase
            .getGroupByInterested(
                interestedTopicTypes, horizontalGroups
            )

        horizontalGroupByType = horizontalGroups
        interestedHorizontalGroup = interestedGroup

        return interestedGroup
    }
    fun getHorizontalGroupByType(chosenType: TopicType): HorizontalGroup {
        return topicDatabase.getGroupByType(
            chosenType,
            horizontalGroupByType
        )
    }
    fun getInterestedHorizontalGroup() = interestedHorizontalGroup

    fun getTopicsByTitle(titleToFind: String): List<Topic> {
        return topicDatabase.getTopicByTitle(
            titleToFind,
            topicsAllTypes,
            horizontalGroupByType
        )
    }
}