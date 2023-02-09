package pl.salo.stoneglish.presentation.core.cards

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import javax.inject.Inject

@HiltViewModel
class CardsTestViewModel @Inject constructor(private val repository: DatabaseRepository) :
    ViewModel() {

    private lateinit var counter: Counter
    private var cardsToTest: MutableList<Card> = mutableListOf()
    private var polishWords: List<String> = listOf()

    private var firstTestFlow = mutableListOf<TestViewData>()

    private val _progressBarState = MutableLiveData<Boolean>()
    val progressBarState: LiveData<Boolean>
        get() = _progressBarState

    private val _wordCounterState = MutableLiveData<String>()
    val wordCounterState: LiveData<String>
        get() = _wordCounterState

    private val _onTestDataChanged = MutableLiveData<TestViewData>()
    val onTestDataChanged: LiveData<TestViewData>
        get() = _onTestDataChanged

    private val _onButtonsColorUpdated = MutableLiveData<Unit>()
    val onButtonsColorUpdated: LiveData<Unit>
        get() = _onButtonsColorUpdated

    private val _onFinish = MutableLiveData<String>()
    val onFinish: LiveData<String>
        get() = _onFinish


    init {
        viewModelScope.launch {
            _progressBarState.postValue(true)
            withContext(IO) {
                polishWords = repository.getListOfPolishWords()
            }
            _progressBarState.postValue(false)

            startTest()
        }
    }

    private fun startTest() {

        cardsToTest.shuffle()
        firstTestFlow = cardsToTest.map {
            val otherWords = polishWords.shuffled().take(2).toList()
            TestViewData(it.word, it.firstTranslation, otherWords)
        } as MutableList<TestViewData>
        counter.flows--
        val item = firstTestFlow[counter.flows]

        _onTestDataChanged.postValue(item)
        _wordCounterState.postValue("${firstTestFlow.size}/${counter.wordsListSize}")
    }

    fun onNext(isCorrect: Boolean) = viewModelScope.launch {

        delay(1000)
        _onButtonsColorUpdated.postValue(Unit)
        val currentItem = onTestDataChanged.value ?: return@launch

        Log.d("CardsTestViewModel", "onNext - isCorrect: $isCorrect")

        counter.flows--
        currentItem.isCorrect = isCorrect

        if (isCorrect) {
            val actualWordCount = --counter.actualWordsSize
            _wordCounterState.postValue("$actualWordCount/${counter.wordsListSize}")
        }

        if (counter.flows > -1) {
            _onTestDataChanged.postValue(firstTestFlow[counter.flows])
        } else {
            val newTestFlow = firstTestFlow.filter { it.isCorrect == true }.toMutableList()

            if (newTestFlow.isNotEmpty()) {
                firstTestFlow.removeAll(newTestFlow)
                if (firstTestFlow.isEmpty()) {
                    Log.d("CardsTestViewModel", "finish")

                    _onFinish.postValue("You have learned ${counter.wordsListSize} words")
                } else {
                    counter.flows = firstTestFlow.size - 1
                    _onTestDataChanged.postValue(firstTestFlow[counter.flows])
                }
            } else {
                counter.flows = firstTestFlow.size - 1
                _onTestDataChanged.postValue(firstTestFlow[counter.flows])
            }

        }

    }

    fun initCards(cards: List<Card>) {
        cardsToTest = cards.toMutableList()
        counter = Counter(cards.size)
    }

}

data class TestViewData(
    val word: String,
    val correctTranslation: String,
    val incorrectTranslations: List<String>,
    var isCorrect: Boolean? = null
)

data class Counter(
    var flows: Int = 0,
    var actualWordsSize: Int = 0,
    val wordsListSize: Int = 0
) {
    constructor(count: Int) : this(count, count, count)
}
