package pl.salo.stoneglish.presentation.core.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.use_cases.DatabaseUseCases
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val databaseUseCases: DatabaseUseCases) :
    ViewModel() {

    private val _currentUser = MutableLiveData<Resource<User>>()
    val currentUser: LiveData<Resource<User>>
        get() = _currentUser

    init {
        getCurrentUser()
    }

    fun getCurrentUser() {
        databaseUseCases.getCurrentUserUseCase().onEach {
            _currentUser.postValue(it)
        }.launchIn(viewModelScope)
    }
}