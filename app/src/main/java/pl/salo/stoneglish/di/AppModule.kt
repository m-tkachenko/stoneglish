package pl.salo.stoneglish.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ViewModelScoped
import pl.salo.stoneglish.R
import dagger.hilt.components.SingletonComponent
import pl.salo.stoneglish.data.firebase.AuthServiceImpl
import pl.salo.stoneglish.data.repository.AuthRepositoryImpl
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.services.AuthService
import pl.salo.stoneglish.domain.use_cases.AuthUseCases
import pl.salo.stoneglish.domain.use_cases.auth.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Firebase super-duper functions
    @Provides
    fun providesFirebaseAuth() = Firebase.auth

    // Services buper-shmuper functions
    @Provides
    fun providesAuthService(auth: FirebaseAuth): AuthService {
        return AuthServiceImpl(auth)
    }

    // Repositories duper-super functions
    @Provides
    fun providesAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepositoryImpl(authService)
    }

    // Use-Cases shmuper-super functions
    @Provides
    fun providesAuthUseCases(authRepository: AuthRepository) = AuthUseCases(
        emailSignIn = UserEmailSignInUseCase(authRepository),
        emailSignUp = UserEmailSignUpUseCase(authRepository),
        googleSignIn = UserGoogleSignInUseCase(authRepository),
        facebookSignIn = UserFacebookSignInUseCase(authRepository),
        signOut = UserSignOutUseCase(authRepository),
        isUserAuthenticated = UserAlreadyAuthenticatedUseCase(authRepository)
    )
}