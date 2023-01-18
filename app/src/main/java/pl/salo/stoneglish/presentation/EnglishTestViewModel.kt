package pl.salo.stoneglish.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.salo.stoneglish.data.repository.SignUpDataRepository
import pl.salo.stoneglish.presentation.EnglishTestViewModel.Companion.EMPTY_SPACE
import javax.inject.Inject

@HiltViewModel
class EnglishTestViewModel @Inject constructor(val signUpDataRepository: SignUpDataRepository) :
    ViewModel() {
    private val testFlow = TestPage(0)
    private var counter = 0

    private val _onQuestionChanged = MutableLiveData<Question>()
    val onQuestionChanged: LiveData<Question>
        get() = _onQuestionChanged

    private val _questionCount = MutableLiveData<Pair<Int, Int>>()
    val questionCount: LiveData<Pair<Int, Int>>
        get() = _questionCount

    private val _onTestFinished = MutableLiveData<String>()
    val onTestFinished: LiveData<String>
        get() = _onTestFinished

    fun start() {
        counter = testFlow.listQuestions.size - 1
        testFlow.listQuestions.shuffled()
        _onQuestionChanged.postValue(testFlow.listQuestions[counter])
        _questionCount.postValue(Pair(1, testFlow.listQuestions.size))
    }

    fun next(isCorrect: Boolean) {
        testFlow.listQuestions[counter].isCorrect = isCorrect
        if (isCorrect) testFlow.testScore++
        Log.d(TAG, "isCorrect: $isCorrect")
        counter--
        if (counter > -1) {
            val pair = questionCount.value
            val first = pair?.first!! + 1
            _questionCount.postValue(Pair(first, pair.second))
            _onQuestionChanged.postValue(testFlow.listQuestions[counter])
        } else {
            val result = when (testFlow.testScore) {
                in 0..6 -> {
                    EnglishLevelType.A0
                }
                in 7..11 -> {
                    EnglishLevelType.A1
                }
                in 12..18 -> {
                    EnglishLevelType.A2
                }
                in 19..25 -> {
                    EnglishLevelType.B1
                }
                in 26..29 -> {
                    EnglishLevelType.B2
                }
                else -> {
                    EnglishLevelType.C1
                }
            }
            signUpDataRepository.setEnglishLevel(result.levelName)
            _onTestFinished.postValue("Your English level is ${result.levelName}")
            Log.d(TAG, "result: ${result.levelName}")
        }
    }

    companion object {
        const val TAG = "EnglishTestViewModel"
        const val EMPTY_SPACE = "______"
    }
}

data class TestPage(
    var testScore: Int, val listQuestions: List<Question> = listOf(
        Question("Example with $EMPTY_SPACE?", "Example", "Example 2", "Example 3")
    )
)

data class Question(
    val question: String,
    val correctAnswer: String,
    val firstIncorrectAnswer: String,
    val secondIncorrectAnswer: String,
    var isCorrect: Boolean? = null
)

enum class EnglishLevelType(val levelName: String) {
    A0("Starter"),
    A1("Elementary"),
    A2("Pre-intermediate"),
    B1("Intermediate"),
    B2("Upper intermediate"),
    C1("Advanced")
}
