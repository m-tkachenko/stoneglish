package pl.salo.stoneglish.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import pl.salo.stoneglish.R
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.use_cases.AuthUseCases
import javax.inject.Inject

private const val TAG = "AuthViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: AuthUseCases
) : ViewModel() {
    private val _authState = MutableLiveData<Resource<Boolean>>()
    val authState: LiveData<Resource<Boolean>>
        get() = _authState

    // signIn and signUp functions
    fun signUpUsingEmailAndPassword(email: String, password: String) {
        auth.emailSignUp(email, password).onEach { result ->
            _authState.postValue(result)
        }.launchIn(viewModelScope)
    }

    fun signInUsingEmailAndPassword(email: String, password: String) {
        auth.emailSignIn(email, password).onEach { result ->
            _authState.postValue(result)
        }.launchIn(viewModelScope)
    }

    fun signInUsingGoogle(account: GoogleSignInAccount) {
        auth.googleSignIn(account).onEach { result ->
            _authState.postValue(result)
        }.launchIn(viewModelScope)
    }

    fun signInUsingFacebook(token: AccessToken) {
        auth.facebookSignIn(token).onEach { result ->
            _authState.postValue(result)
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
}