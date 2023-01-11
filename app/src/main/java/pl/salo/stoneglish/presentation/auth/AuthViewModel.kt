package pl.salo.stoneglish.presentation.auth

import android.util.Log
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
import pl.salo.stoneglish.data.repository.SignUpDataRepository
import pl.salo.stoneglish.domain.model.auth.SignUpCategoryItem
import pl.salo.stoneglish.domain.use_cases.AuthUseCases
import pl.salo.stoneglish.domain.use_cases.database.WriteUserDataUseCase
import pl.salo.stoneglish.util.Event
import javax.inject.Inject

private const val TAG = "AuthViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: AuthUseCases,
    private val writeUserDataUseCase: WriteUserDataUseCase,
    private val signUpDataRepository: SignUpDataRepository
) : ViewModel() {
    private val _authState = MutableLiveData<Event<Resource<Boolean>>>()
    val authState: LiveData<Event<Resource<Boolean>>>
        get() = _authState

    private val _categories = MutableLiveData<Event<Resource<List<SignUpCategoryItem>>>>()
    val categories: LiveData<Event<Resource<List<SignUpCategoryItem>>>>
        get() = _categories

    private val _onSignOut = MutableLiveData<Resource<Unit>>()
    val onSignOut: LiveData<Resource<Unit>>
        get() = _onSignOut

    // signIn and signUp functions
    fun signUpUsingEmailAndPassword() {
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

    fun getCategories(){
        auth.signUpDataGetCategoriesUseCase().onEach { categories ->
            _categories.value = Event(categories)
        }.launchIn(viewModelScope)
    }

    fun getSelectedCategories(): List<String>{
        val categories = signUpDataRepository.getSignUpData().interestedTopics.map {
            it.title
        }
        Log.d(TAG, "getSelectedCategories: ${categories}")
        return categories
    }

    fun setCategoryState(category: SignUpCategoryItem){
        auth.signUpDataSetCategoryState(category).onEach { result ->
            Log.d(TAG, "setCategoryState: $result")
            if(result is Resource.Success){
                getCategories()
            }else{
                _authState.postValue(Event(result as Resource<Boolean>))
            }
        }.launchIn(viewModelScope)
    }

    fun signOut() {
        auth.signOut().onEach {
            _onSignOut.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun clearData() {
        signUpDataRepository.clearCategories()
    }


}