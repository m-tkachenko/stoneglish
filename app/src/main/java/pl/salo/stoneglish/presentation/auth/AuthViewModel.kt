package pl.salo.stoneglish.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.use_cases.AuthUseCases
import javax.inject.Inject

const val TAG = "AuthViewModel"
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: AuthUseCases
) : ViewModel() {
    private val _authState = MutableLiveData<Resource<Boolean>>()
    val authState: LiveData<Resource<Boolean>>
        get() = _authState

    fun signUpUsingEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            auth.emailSignUp(email, password).collect { result ->
                _authState.postValue(result)
            }
        }
    }

    fun signInUsingEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            auth.emailSignIn(email, password).collect { result ->
                _authState.postValue(result)
            }
        }
    }

    fun signInUsingGoogle(account: GoogleSignInAccount) {
        viewModelScope.launch {
            auth.googleSignIn(account).collect { result ->
                _authState.postValue(result)
            }
        }
    }

    fun signInUsingFacebook(token: AccessToken) {
        viewModelScope.launch {
            auth.facebookSignIn(token).collect { result ->
                _authState.postValue(result)
            }
        }
    }
}