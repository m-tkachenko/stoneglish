package pl.salo.stoneglish.domain.repository

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun isUserAlreadyAuthenticated(): Boolean

    suspend fun emailSignIn(email: String, password: String): FirebaseUser?
    suspend fun signUp(email: String, password: String): FirebaseUser?
    suspend fun googleSignIn(account: GoogleSignInAccount): FirebaseUser?
    suspend fun facebookSignIn(token: AccessToken): FirebaseUser?
    suspend fun signOut()
    suspend fun isEmailExist(email: String): Boolean
}