package pl.salo.stoneglish.presentation.core

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.use_cases.SetClickableTextUseCase
import javax.inject.Inject

@HiltViewModel
class CoreViewModel @Inject constructor(private val setClickableTextUseCase: SetClickableTextUseCase) : ViewModel() {

    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

fun setClickableText(content:String, textView: TextView){
    setClickableTextUseCase(content, textView).onEach {
        when(it){
            is Resource.Loading -> {}
            is Resource.Success -> {
                _word.postValue(it.data ?: "Oops")
            }
            is Resource.Error -> {
                _word.postValue("Something went wrong :(")
            }
        }
    }.launchIn(viewModelScope)
}
}