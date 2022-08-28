package pl.salo.stoneglish.domain.repository

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow
import pl.salo.stoneglish.common.Resource
import javax.inject.Singleton

interface AuthRepository {
    fun isUserAlreadyAuthenticated(): Boolean

    suspend fun emailSignIn(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun signUp(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun googleSignIn(account: GoogleSignInAccount): Flow<Resource<Boolean>>
    suspend fun facebookSignIn(token: AccessToken): Flow<Resource<Boolean>>
    suspend fun signOut()
}