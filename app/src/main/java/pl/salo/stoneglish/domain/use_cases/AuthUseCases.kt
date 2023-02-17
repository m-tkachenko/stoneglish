package pl.salo.stoneglish.domain.use_cases

import pl.salo.stoneglish.domain.use_cases.auth.*

data class AuthUseCases(
    val emailSignIn: UserEmailSignInUseCase,
    val emailSignUp: UserEmailSignUpUseCase,
    val signOut: UserSignOutUseCase,
    val isUserAuthenticated: UserAlreadyAuthenticatedUseCase,
    val signUpGetData: SignUpGetDataUseCase,
    val signUpDataSetAgeAndNameUseCase: SignUpDataSetAgeAndNameUseCase,
    val signUpDataSetEmailAndPasswordUseCase: SignUpDataSetEmailAndPasswordUseCase,
    val signUpDataSetEnglishLevelUseCase: SignUpDataSetEnglishLevelUseCase,
    val signUpDataGetCategoriesUseCase: SignUpDataGetCategoriesUseCase,
    val signUpDataSetCategoryState: SignUpDataSetCategoryState
)