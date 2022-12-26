package pl.salo.stoneglish.presentation.core.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.use_cases.CardsUseCases
import pl.salo.stoneglish.domain.use_cases.HomeUseCases
import pl.salo.stoneglish.util.Event
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cardDatabase: CardsUseCases,
    private val homeDatabase: HomeUseCases
) : ViewModel() {
    private val _modulesState = MutableLiveData<Event<Resource<List<String>>>>()
    val modulesState: LiveData<Event<Resource<List<String>>>>
        get() = _modulesState

    private val _addCardState = MutableLiveData<Event<Resource<Boolean>>>()
    val addCardState: LiveData<Event<Resource<Boolean>>>
        get() = _addCardState

    private val _dailyCardState = MutableLiveData<Event<Resource<List<Card>>>>()
    val dailyCardState: LiveData<Event<Resource<List<Card>>>>
        get() = _dailyCardState

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
}