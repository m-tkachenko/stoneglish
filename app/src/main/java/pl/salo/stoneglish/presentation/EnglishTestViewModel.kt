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
        const val EMPTY_SPACE = "___"
    }
}

data class TestPage(
    var testScore: Int, val listQuestions: List<Question> = listOf(
        Question("30.She $EMPTY_SPACE to help me with the decorating.", "offered", "invited", "suggested"),
        Question("29.How did you manage to cook $EMPTY_SPACE a good meal?", "such", "that", "so"),
        Question("28.They $EMPTY_SPACE heard us coming, we were making a lot of noise.", "must have", "might", "must"),
        Question("27.The solution had been found, $EMPTY_SPACE we hadn’t realised it.", "although", " however", "even"),
        Question("26.This is going to be my chance to $EMPTY_SPACE any difficulties.", "sort out", "repair", "improve"),
        Question("25.It $EMPTY_SPACE correctly.", "hasn’t been done", "hasn’t done", "not been done"),
        Question("24.Your work is $EMPTY_SPACE better.", "getting", "doing", "being"),
        Question("23.Who was $EMPTY_SPACE the door?", "at", "in", "of"),
        Question("22.I’m writing $EMPTY_SPACE ask you to explain.", "in order to", "because of", "for"),
        Question("21.If we get up in time, $EMPTY_SPACE catch the train.", "we’ll catch", "we caught", "we catch"),
        Question("20.$EMPTY_SPACE does your boyfriend look like?", "What", "How", "Why"),
        Question("19.She $EMPTY_SPACE like her sister.", "isn’t", "look", "can look"),
        Question("18.Were you $EMPTY_SPACE to open the door?", "able", "possible", "can"),
        Question("17.Everybody $EMPTY_SPACE wear a seat belt in the car.", "must", "don’t have to", "mustn’t"),
        Question("16.They are going $EMPTY_SPACE in Canada next month.", "to be", "be", "will be"),
        Question("15.I like this song. $EMPTY_SPACE do I.", "So", "Neither", "Either"),
        Question("14.Joanna $EMPTY_SPACE to the radio all morning.", "listened", "heard", "listening"),
        Question("13.They $EMPTY_SPACE ever check their emails.", " hardly", " harder", "hard"),
        Question("12.Have you $EMPTY_SPACE been to Greece?", "ever", "always", "soon"),
        Question("11.I have never met an actor $EMPTY_SPACE.", "before", "already", "after"),
        Question("10.Their car is $EMPTY_SPACE biggest on the road.", "the", "this", "than"),
        Question("9.I $EMPTY_SPACE in an armchair at the moment.", "am sitting", "sit", "sitting"),
        Question("8.Today is the $EMPTY_SPACE of April.", "third", "day three", "three"),
        Question("7.Do you like the red $EMPTY_SPACE ?", "one", "it", "that"),
        Question("6.He $EMPTY_SPACE there for a long time.", "lived", "live", "living"),
        Question("5.Have you got a pencil? No, I $EMPTY_SPACE.", "haven’t", "am", "got"),
        Question("4.They don’t have $EMPTY_SPACE sugar.", "any", "got", "a"),
        Question("3.Friday, Saturday, Sunday, $EMPTY_SPACE.", "Monday", "Wednesday", "Tuesday"),
        Question("2.$EMPTY_SPACE he like this film?", "Does", "Have", "Do"),
        Question("1.$EMPTY_SPACE name is Katarzyna.", "My", "I", "Me")
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
