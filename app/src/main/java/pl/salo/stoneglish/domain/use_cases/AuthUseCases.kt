package pl.salo.stoneglish.domain.use_cases

import pl.salo.stoneglish.domain.use_cases.auth.*

data class AuthUseCases(
    val emailSignIn: UserEmailSignInUseCase,
    val emailSignUp: UserEmailSignUpUseCase,
    val googleSignIn: UserGoogleSignInUseCase,
    val signOut: UserSignOutUseCase,
    val isUserAuthenticated: UserAlreadyAuthenticatedUseCase,
    val signUpDataSetAgeAndNameUseCase: SignUpDataSetAgeAndNameUseCase,
    val signUpDataSetEmailAndPasswordUseCase: SignUpDataSetEmailAndPasswordUseCase,
    val signUpDataSetEnglishLevelUseCase: SignUpDataSetEnglishLevelUseCase,
    val signUpDataSetTopicsUseCase: SignUpDataSetTopicsUseCase,
)