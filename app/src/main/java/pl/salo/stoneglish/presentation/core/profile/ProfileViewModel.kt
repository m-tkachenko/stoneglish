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
import pl.salo.stoneglish.util.Event
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val databaseUseCases: DatabaseUseCases) :
    ViewModel() {

    private val _currentUser = MutableLiveData<Resource<User>>()
    val currentUser: LiveData<Resource<User>>
        get() = _currentUser

    private val _onUserDataChanged = MutableLiveData<Resource<Unit>>()
    val onUserDataChanged: LiveData<Resource<Unit>>
        get() = _onUserDataChanged

    private val _fieldToChange = MutableLiveData<Field>()
    val fieldToChange: LiveData<Field>
        get() = _fieldToChange

    private val _onUserTextChanged = MutableLiveData<Event<Resource<Unit>>>()
    val onUserTextChanged: LiveData<Event<Resource<Unit>>>
        get() = _onUserTextChanged

    fun getCurrentUser() {
        databaseUseCases.getCurrentUserUseCase().onEach {
            _currentUser.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun selectField(field: Field){
        _fieldToChange.postValue(field)
    }

    fun changeField(newFieldText: String) {

        val user = currentUser.value?.data ?: return

        if (newFieldText.isBlank()) {
            _onUserTextChanged.postValue(Event(Resource.Error(null, "Field is empty")))
            return
        }



        when(fieldToChange.value){
            Field.NAME ->{

                databaseUseCases.updateUserFieldUseCase(user.id, Field.NAME, newFieldText).onEach {
                    getCurrentUser()
                    _onUserTextChanged.postValue(Event(it))
                }.launchIn(viewModelScope)
            }
            Field.AGE -> {

                if (newFieldText.isBlank()) {
                    _onUserTextChanged.postValue(Event(Resource.Error(null, "Age field is empty")))
                    return
                }

                if (newFieldText.toInt() > 99 || newFieldText.toInt() == 0) {
                    _onUserTextChanged.postValue(Event(Resource.Error(null, "Invalid age")))
                    return
                }

                user.age = newFieldText.toInt()
                databaseUseCases.updateUserFieldUseCase(user.id, Field.AGE, newFieldText).onEach {
                    getCurrentUser()
                    _onUserTextChanged.postValue(Event(it))
                }.launchIn(viewModelScope)
            }
            else -> {

            }
        }
    }

    fun updateUserCategories(categories: List<String>) {
        val user = currentUser.value?.data ?: return

        databaseUseCases.updateUserCategories(user.id, categories).onEach {
            _onUserDataChanged.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun clearCategories() {
        _onUserDataChanged.value = Resource.Loading()
    }
}

enum class Field(val text: String){
    NAME("Change name"),
    AGE("Change age")
}