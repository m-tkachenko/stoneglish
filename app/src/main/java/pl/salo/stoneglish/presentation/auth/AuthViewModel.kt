package pl.salo.stoneglish.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "AuthViewModel"
@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel(){

    private val _authState = MutableLiveData<Unit>()
    val authState: LiveData<Unit>
        get() = _authState

    fun login(email: String, password: String) = viewModelScope.launch{
        Log.d(TAG, "Trying to login")
        delay(2000)
        _authState.postValue(Unit)
    }

    fun register(email: String, password: String) = viewModelScope.launch{
        Log.d(TAG, "Trying to register")
        delay(2000)
        _authState.postValue(Unit)
    }

}