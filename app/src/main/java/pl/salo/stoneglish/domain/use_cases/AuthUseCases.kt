package pl.salo.stoneglish.domain.use_cases

import pl.salo.stoneglish.domain.use_cases.auth.*

data class AuthUseCases(
    val emailSignIn: UserEmailSignInUseCase,
    val emailSignUp: UserEmailSignUpUseCase,
    val googleSignIn: UserGoogleSignInUseCase,
    val facebookSignIn: UserFacebookSignInUseCase,
    val signOut: UserSignOutUseCase,
    val isUserAuthenticated: UserAlreadyAuthenticatedUseCase
)