package pl.salo.stoneglish.domain.services

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface AuthService {
    fun isUserAlreadyAuthenticated(): Boolean

    suspend fun emailSignIn(email: String, password: String): FirebaseUser?
    suspend fun signUp(email: String, password: String): FirebaseUser?
    suspend fun signOut():Boolean
    suspend fun isEmailExist(email: String):Boolean
    suspend fun getUserId(): String?
    suspend fun forgotPassword(email: String)
}