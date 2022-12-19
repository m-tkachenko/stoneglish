package pl.salo.stoneglish.presentation.core.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.dictionary.Word
import pl.salo.stoneglish.domain.use_cases.DictionaryUseCases
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(private val dictionaryUseCases: DictionaryUseCases) :
    ViewModel() {

    private val _wordData = MutableLiveData<Resource<Word>>()
    val wordData: LiveData<Resource<Word>>
        get() = _wordData

    private val _playingAudio = MutableLiveData<Resource<Unit>>()
    val playingAudio: LiveData<Resource<Unit>>
        get() = _playingAudio

    fun getWordData(query:String){
        _wordData.value = Resource.Loading()
        dictionaryUseCases.dictionaryGetWordDataUseCase(query).onEach { result ->
            _wordData.postValue(result)
        }.launchIn(viewModelScope)
    }

    fun runAudio() {
        _playingAudio.value = Resource.Loading()
        dictionaryUseCases.playAudioByUrl(wordData.value?.data?.phonetics?.first()?.audio).onEach { result ->
            _playingAudio.postValue(result)
        }.launchIn(viewModelScope)
    }
}