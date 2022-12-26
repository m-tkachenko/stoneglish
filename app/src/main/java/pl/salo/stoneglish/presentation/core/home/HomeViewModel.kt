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
import pl.salo.stoneglish.util.Event
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val database: CardsUseCases
) : ViewModel() {
    private val _modulesState = MutableLiveData<Event<Resource<List<String>>>>()
    val modulesState: LiveData<Event<Resource<List<String>>>>
        get() = _modulesState

    private val _addCardState = MutableLiveData<Event<Resource<Boolean>>>()
    val addCardState: LiveData<Event<Resource<Boolean>>>
        get() = _addCardState

    fun downloadModules() {
        database.modulesList().onEach { modules ->
            _modulesState.postValue(Event(modules))
        }.launchIn(viewModelScope)
    }

    fun addNewCard(newWord: String, moduleName: String) {
        database.addNewCard(
            card = Card(word = newWord),
            moduleName = moduleName
        ).onEach { status ->
            _addCardState.postValue(Event(status))
        }.launchIn(viewModelScope)
    }
}