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
import pl.salo.stoneglish.domain.use_cases.CardsUseCases
import javax.inject.Inject

@HiltViewModel
class CreateModuleViewModel @Inject constructor(
    private val cardsUseCases: CardsUseCases
) : ViewModel() {

    private val _cards = MutableLiveData<Card>()
    val cards: LiveData<Card>
        get() = _cards

    private val _finish = MutableLiveData<Unit>()
    val finish: LiveData<Unit>
        get() = _finish

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private var cardData: Card? = null

    fun setUpCard(card: Card) {
        cardData = card
    }

    fun updateCardData(cardTranslation: String) {
        val currentCard = cardData ?: return

        currentCard.firstTranslation = cardTranslation

        _cards.postValue(currentCard)
    }

    fun writeModule(cards: List<Card>, moduleName: String) {
        cardsUseCases.writeCardsUseCase(cards, moduleName).onEach {
            when (it) {
                is Resource.Loading -> {
                    _loading.postValue(true)
                }
                is Resource.Error -> {
                    _loading.postValue(false)
                    _toast.postValue(it.message.toString())
                }
                is Resource.Success -> {
                    _loading.postValue(false)
                    _finish.postValue(Unit)
                }
            }
        }.launchIn(viewModelScope)
    }

}