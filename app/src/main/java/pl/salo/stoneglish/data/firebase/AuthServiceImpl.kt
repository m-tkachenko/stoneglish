package pl.salo.stoneglish.data.firebase

import android.util.Log
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FacebookAuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.services.AuthService
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
): AuthService {
    override suspend fun signUp(
        email: String,
        password: String
    ): Resource<Boolean> {
        var signUpResult: Resource<Boolean> = Resource.Loading()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { signUpTaskResult ->
                signUpResult =
                    if (signUpTaskResult.isSuccessful) {
                        Log.d(TAG, "SignUp : Success")
                        Resource.Success(data = true)
                    } else {
                        val error = signUpTaskResult.exception?.message ?: "Unexpected error"

                        Log.e(TAG, "SignUp : Failure : Error = $error")
                        Resource.Error(
                            data = false,
                            message = error
                        )
                    }
            }

        return signUpResult
    }

    override suspend fun emailSignIn(
        email: String,
        password: String
    ): Resource<Boolean> {
        var emailSignInResult: Resource<Boolean> = Resource.Loading()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { emailAuthResult ->
                emailSignInResult =
                    if (emailAuthResult.isSuccessful) {
                        Log.d(TAG, "EmailSignIn : Success")
                        Resource.Success(data = true)
                    } else {
                        val error = emailAuthResult.exception?.message ?: "Unexpected error"

                        Log.e(TAG, "EmailSignIn : Failure : Error = $error")
                        Resource.Error(
                            data = false,
                            message = error
                        )
                    }
            }

        return emailSignInResult
    }

    override suspend fun googleSignIn(account: GoogleSignInAccount): Resource<Boolean> {
        var googleSignInResult: Resource<Boolean> = Resource.Loading()
        val googleCredential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(googleCredential)
            .addOnCompleteListener { googleAuthResult ->
                googleSignInResult =
                    if (googleAuthResult.isSuccessful) {
                        Log.d(TAG, "GoogleSignIn : Success")
                        Resource.Success(data = true)
                    } else {
                        val error = googleAuthResult.exception?.message ?: "Unexpected error"

                        Log.e(TAG, "GoogleSignIn : Failure : Error = $error")
                        Resource.Error(
                            data = false,
                            message = error
                        )
                    }
            }

        return googleSignInResult
    }

    override suspend fun facebookSignIn(token: AccessToken): Resource<Boolean> {
        var facebookSignInResult: Resource<Boolean> = Resource.Loading()
        val facebookCredential = FacebookAuthProvider.getCredential(token.token)

        auth.signInWithCredential(facebookCredential)
            .addOnCompleteListener { facebookResult ->
                facebookSignInResult =
                    if (facebookResult.isSuccessful) {
                        Log.d(TAG, "FacebookSignIn : Success")
                        Resource.Success(data = true)
                    } else {
                        val error = facebookResult.exception?.message ?: "Unexpected error"

                        Log.e(TAG, "FacebookSignIn : Failure : Error = $error")
                        Resource.Error(
                            data = false,
                            message = error
                        )
                    }
            }

        return facebookSignInResult
    }

    override suspend fun signOut() = auth.signOut()
    override fun isUserAlreadyAuthenticated(): Boolean = auth.currentUser != null

    companion object {
        private const val TAG = "AuthService"
    }
}