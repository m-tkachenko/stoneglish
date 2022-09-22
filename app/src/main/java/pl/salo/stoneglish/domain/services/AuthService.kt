package pl.salo.stoneglish.domain.services

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface AuthService {
    fun isUserAlreadyAuthenticated(): Boolean

    suspend fun emailSignIn(email: String, password: String): FirebaseUser?
    suspend fun signUp(email: String, password: String): FirebaseUser?
    suspend fun googleSignIn(account: GoogleSignInAccount): FirebaseUser?
    suspend fun facebookSignIn(token: AccessToken): FirebaseUser?
    suspend fun signOut()
    suspend fun isEmailExist(email: String):Boolean
}