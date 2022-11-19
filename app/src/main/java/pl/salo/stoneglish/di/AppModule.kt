package pl.salo.stoneglish.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.salo.stoneglish.data.firebase.AuthServiceImpl
import pl.salo.stoneglish.data.firebase.DatabaseServiceImpl
import pl.salo.stoneglish.data.repository.AuthRepositoryImpl
import pl.salo.stoneglish.data.repository.DatabaseRepositoryImpl
import pl.salo.stoneglish.data.repository.DictionaryRepositoryImpl
import pl.salo.stoneglish.data.repository.SignUpDataRepository
import pl.salo.stoneglish.data.retrofit.DictionaryApiService
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import pl.salo.stoneglish.domain.repository.DictionaryRepository
import pl.salo.stoneglish.domain.services.AuthService
import pl.salo.stoneglish.domain.services.DatabaseService
import pl.salo.stoneglish.domain.use_cases.AuthUseCases
import pl.salo.stoneglish.domain.use_cases.DictionaryUseCases
import pl.salo.stoneglish.domain.use_cases.auth.*
import pl.salo.stoneglish.domain.use_cases.database.WriteUserDataUseCase
import pl.salo.stoneglish.domain.use_cases.dictionary.DictionaryGetWordDataUseCase
import pl.salo.stoneglish.domain.use_cases.dictionary.PlayAudioByUrl
import pl.salo.stoneglish.util.Constants
import pl.salo.stoneglish.util.DataMapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Firebase super-duper functions
    @Singleton
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
    fun providesAuthUseCases(
        authRepository: AuthRepository,
        signUpDataRepository: SignUpDataRepository
    ) = AuthUseCases(
        emailSignIn = UserEmailSignInUseCase(authRepository),
        emailSignUp = UserEmailSignUpUseCase(authRepository),
        googleSignIn = UserGoogleSignInUseCase(authRepository),
        signOut = UserSignOutUseCase(authRepository),
        isUserAuthenticated = UserAlreadyAuthenticatedUseCase(authRepository),
        signUpGetData = SignUpGetDataUseCase(signUpDataRepository),
        signUpDataSetAgeAndNameUseCase = SignUpDataSetAgeAndNameUseCase(signUpDataRepository),
        signUpDataSetEnglishLevelUseCase = SignUpDataSetEnglishLevelUseCase(signUpDataRepository),
        signUpDataSetEmailAndPasswordUseCase = SignUpDataSetEmailAndPasswordUseCase(
            signUpDataRepository,
            authRepository
        ),
        signUpDataGetCategoriesUseCase = SignUpDataGetCategoriesUseCase(signUpDataRepository),
        signUpDataSetCategoryState = SignUpDataSetCategoryState(signUpDataRepository)
    )

    @Singleton
    @Provides
    fun provideSignUpDataRepository() = SignUpDataRepository()

    @Provides
    fun provideDataMapper() = DataMapper()

    /**
     * FirebaseDatabase provides
     */

    @Singleton
    @Provides
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance()

    @Singleton
    @Provides
    fun provideDatabaseService(firebaseDatabase: FirebaseDatabase): DatabaseService{
        return DatabaseServiceImpl(firebaseDatabase.reference)
    }

    @Provides
    fun provideWriteUserUseCase(databaseRepository: DatabaseRepository, mapper: DataMapper): WriteUserDataUseCase{
        return WriteUserDataUseCase(databaseRepository, mapper)
    }

    @Provides
    fun provideFirebaseDatabaseRepository(databaseService: DatabaseService): DatabaseRepository{
        return DatabaseRepositoryImpl(databaseService)
    }

    //Dictionary
    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApiService = Retrofit.Builder()
            .baseUrl(Constants.DICTIONARY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApiService::class.java)

    @Provides
    @Singleton
    fun provideDictionaryRepository(dictionaryApiService: DictionaryApiService): DictionaryRepository {
        return DictionaryRepositoryImpl(dictionaryApiService)
    }

    @Provides
    fun providesDictionaryUseCases(
        dictionaryRepository: DictionaryRepository
    ) = DictionaryUseCases(
        dictionaryGetWordDataUseCase = DictionaryGetWordDataUseCase(dictionaryRepository),
        playAudioByUrl = PlayAudioByUrl()
    )
}