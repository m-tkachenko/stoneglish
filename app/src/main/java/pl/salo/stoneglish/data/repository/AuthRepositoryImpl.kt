package pl.salo.stoneglish.data.repository

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.services.AuthService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
): AuthRepository {
    override suspend fun signUp(email: String, password: String): Flow<Resource<Boolean>> {
        return authService.signUp(email, password)
    }

    override suspend fun emailSignIn(email: String, password: String): Flow<Resource<Boolean>> {
        return authService.emailSignIn(email, password)
    }

    override suspend fun googleSignIn(account: GoogleSignInAccount): Flow<Resource<Boolean>> {
        return authService.googleSignIn(account)
    }

    override suspend fun facebookSignIn(token: AccessToken): Flow<Resource<Boolean>> {
        return authService.facebookSignIn(token)
    }

    override suspend fun signOut() = authService.signOut()
    override fun isUserAlreadyAuthenticated() = authService.isUserAlreadyAuthenticated()
}