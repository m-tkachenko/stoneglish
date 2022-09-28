package pl.salo.stoneglish.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import pl.salo.stoneglish.R
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.use_cases.AuthUseCases
import pl.salo.stoneglish.domain.use_cases.database.WriteUserDataUseCase
import pl.salo.stoneglish.util.Event
import javax.inject.Inject

private const val TAG = "AuthViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: AuthUseCases,
    private val writeUserDataUseCase: WriteUserDataUseCase
) : ViewModel() {
    private val _authState = MutableLiveData<Event<Resource<Boolean>>>()
    val authState: LiveData<Event<Resource<Boolean>>>
        get() = _authState

    // signIn and signUp functions
    private suspend fun signUpUsingEmailAndPassword() {
        _authState.value = Event(Resource.Loading())

        auth.signUpGetData().onEach { signUpData ->
            if (signUpData is Resource.Success) {
                auth.emailSignUp(signUpData.data!!).onEach { result ->
                    if (result is Resource.Success) {
                        writeUserDataUseCase(null, result.data).onEach { res ->
                            _authState.postValue(Event(res as Resource<Boolean>))
                        }.launchIn(viewModelScope)
                    } else {
                        _authState.postValue(Event(result as Resource<Boolean>))
                    }
                }.launchIn(viewModelScope)
            } else {
                _authState.postValue(Event(signUpData as Resource<Boolean>))
            }
        }.launchIn(viewModelScope)
    }

    fun signInUsingEmailAndPassword(email: String, password: String) {
        auth.emailSignIn(email, password).onEach { result ->
            _authState.postValue(Event(result))
        }.launchIn(viewModelScope)
    }

    fun signInUsingGoogle(account: GoogleSignInAccount) {
        auth.googleSignIn(account).onEach { result ->
            _authState.postValue(Event(result))
        }.launchIn(viewModelScope)
    }

    // Some functions for signIn with Google
    fun createGoogleSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(R.string.default_web_client_id.toString())
                    .setFilterByAuthorizedAccounts(true)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    /**
     *  SignUp data functions
     */
    fun saveEmailAndPasswordToCache(email: String, password: String) =
        auth.signUpDataSetEmailAndPasswordUseCase(email, password).onEach { result ->
            _authState.postValue(Event(result as Resource<Boolean>))
        }.launchIn(viewModelScope)


    fun saveNameAndAgeToCache(name: String, age: String) =
        auth.signUpDataSetAgeAndNameUseCase(name, age).onEach { result ->
            _authState.postValue(Event(result as Resource<Boolean>))
        }.launchIn(viewModelScope)


    fun saveUserEnglishLevelToCache(e_level: String) =
        auth.signUpDataSetEnglishLevelUseCase(e_level).onEach { result ->
            _authState.postValue(Event(result as Resource<Boolean>))
        }.launchIn(viewModelScope)

    fun saveUserInterestedTopics(topics: List<String>) =
        auth.signUpDataSetTopicsUseCase(topics).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    signUpUsingEmailAndPassword()
                }
                is Resource.Error -> {
                }
                else -> {}
            }
        }.launchIn(viewModelScope)

}