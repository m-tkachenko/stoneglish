package pl.salo.stoneglish.data.repository

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.services.AuthService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
): AuthRepository {
    override suspend fun signUp(email: String, password: String) =
        authService.signUp(email, password)

    override suspend fun emailSignIn(email: String, password: String) =
        authService.emailSignIn(email, password)

    override suspend fun googleSignIn(account: GoogleSignInAccount) =
        authService.googleSignIn(account)

    override suspend fun facebookSignIn(token: AccessToken) =
        authService.facebookSignIn(token)

    override suspend fun signOut() = authService.signOut()

    override suspend fun isEmailExist(email: String) = authService.isEmailExist(email)

    override fun isUserAlreadyAuthenticated() = authService.isUserAlreadyAuthenticated()
}