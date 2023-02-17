package pl.salo.stoneglish.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.services.AuthService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
): AuthRepository {
    override suspend fun signUp(email: String, password: String) =
        authService.signUp(email, password)

    override suspend fun emailSignIn(email: String, password: String) = authService.emailSignIn(email, password)

    override suspend fun googleSignIn(account: GoogleSignInAccount) =
        authService.googleSignIn(account)

    override suspend fun signOut(): Boolean = authService.signOut()

    override suspend fun isEmailExist(email: String) = authService.isEmailExist(email)
    override suspend fun getUserId() = authService.getUserId()
    override suspend fun forgotPassword(email: String) = authService.forgotPassword(email)


    override fun isUserAlreadyAuthenticated() = authService.isUserAlreadyAuthenticated()
}