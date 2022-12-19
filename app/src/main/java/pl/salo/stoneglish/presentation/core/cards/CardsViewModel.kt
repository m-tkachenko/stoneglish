package pl.salo.stoneglish.presentation.core.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.model.card.Test
import pl.salo.stoneglish.domain.use_cases.CardsUseCases
import pl.salo.stoneglish.util.Event
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val database: CardsUseCases
): ViewModel() {
    private val _modulesState = MutableLiveData<Event<Resource<List<String>>>>()
    val modulesState: LiveData<Event<Resource<List<String>>>>
        get() = _modulesState

    private val _cardsDownloadState = MutableLiveData<Event<Resource<List<Card>>>>()
    val cardsDownloadState: LiveData<Event<Resource<List<Card>>>>
        get() = _cardsDownloadState

    private val _cardUploadState = MutableLiveData<Event<Resource<Boolean>>>()
    val cardUploadState: LiveData<Event<Resource<Boolean>>>
        get() = _cardUploadState

    private val _testsState = MutableLiveData<Event<Resource<List<Test>>>>()
    val testsState: LiveData<Event<Resource<List<Test>>>>
        get() = _testsState

    fun uploadNewCard(card: Card, moduleName: String) {
        database.addNewCard(card, moduleName).onEach { result ->
            _cardUploadState.postValue(Event(result))
        }.launchIn(viewModelScope)
    }

    fun downloadCards(moduleName: String) {
        database.cardsList(moduleName).onEach { cards ->
            _cardsDownloadState.postValue(Event(cards))
        }.launchIn(viewModelScope)
    }

    fun downloadTests(moduleName: String) {
        database.testsList(moduleName).onEach { tests ->
            _testsState.postValue(Event(tests))
        }.launchIn(viewModelScope)
    }

    fun downloadModules() {
        database.modulesList().onEach { modules ->
            _modulesState.postValue(Event(modules))
        }.launchIn(viewModelScope)
    }
}