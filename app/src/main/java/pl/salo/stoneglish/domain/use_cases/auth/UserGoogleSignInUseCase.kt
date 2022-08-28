package pl.salo.stoneglish.domain.use_cases.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.repository.AuthRepository
import javax.inject.Inject

class UserGoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(account: GoogleSignInAccount): Flow<Resource<Boolean>> =
        authRepository.googleSignIn(account)
}