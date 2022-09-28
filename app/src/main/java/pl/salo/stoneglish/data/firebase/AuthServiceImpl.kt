package pl.salo.stoneglish.data.firebase

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.*
import kotlinx.coroutines.tasks.await
import pl.salo.stoneglish.domain.services.AuthService
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
): AuthService {
    override suspend fun signUp(
        email: String,
        password: String
    ): FirebaseUser? =
        auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { signUpTaskResult ->
                if (signUpTaskResult.isSuccessful)
                    Log.d(TAG, "SignUp : Success")
                else
                    Log.e(TAG, "SignUp : Failure : Error = ${signUpTaskResult.exception?.message}")
            }
            .await()
            .user

    override suspend fun emailSignIn(
        email: String,
        password: String
    ): FirebaseUser? =
        auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { emailSignInResult ->
                if (emailSignInResult.isSuccessful)
                    Log.d(TAG, "EmailSignIn : Success")
                else
                    Log.e(TAG, "EmailSignIn : Failure : Error = ${emailSignInResult.exception?.message}")
            }
            .await()
            .user

    override suspend fun googleSignIn(
        account: GoogleSignInAccount
    ): FirebaseUser? =
        auth
            .signInWithCredential(
                GoogleAuthProvider.getCredential(account.idToken, null)
            )
            .addOnCompleteListener { googleSignInResult ->
                if (googleSignInResult.isSuccessful)
                    Log.d(TAG, "GoogleSignIn : Success")
                else
                    Log.e(TAG, "GoogleSignIn : Failure : Error = ${googleSignInResult.exception?.message}")
            }
            .await()
            .user

//    override suspend fun facebookSignIn(
//        token: AccessToken
//    ): FirebaseUser? =
//        auth
//            .signInWithCredential(FacebookAuthProvider.getCredential(token.token))
//            .addOnCompleteListener { facebookSignInResult ->
//                if (facebookSignInResult.isSuccessful)
//                    Log.d(TAG, "FacebookSignIn : Success")
//                else
//                    Log.e(TAG, "FacebookSignIn : Failure : Error = ${facebookSignInResult.exception?.message}")
//            }
//            .await()
//            .user

    override suspend fun signOut() = auth.signOut()

    override suspend fun isEmailExist(email: String):Boolean {
        val result = auth.fetchSignInMethodsForEmail(email).await()
        return result.signInMethods?.isEmpty() != true
    }

    override fun isUserAlreadyAuthenticated(): Boolean = auth.currentUser != null

    companion object {
        private const val TAG = "AuthService"
    }
}