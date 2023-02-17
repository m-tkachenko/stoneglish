package pl.salo.stoneglish.mock_repositories

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import pl.salo.stoneglish.MockUserData
import pl.salo.stoneglish.domain.repository.AuthRepository

class MockAuthRepository: AuthRepository {
    private val mockData = MockUserData()

    override suspend fun getUserId(): String = mockData.user.id

    override fun isUserAlreadyAuthenticated(): Boolean {
        TODO("Not yet implemented")
    }
    override suspend fun emailSignIn(email: String, password: String): FirebaseUser? {
        TODO("Not yet implemented")
    }
    override suspend fun signUp(email: String, password: String): FirebaseUser? {
        TODO("Not yet implemented")
    }
    override suspend fun googleSignIn(account: GoogleSignInAccount): FirebaseUser? {
        TODO("Not yet implemented")
    }
    override suspend fun signOut(): Boolean {
        TODO("Not yet implemented")
    }
    override suspend fun isEmailExist(email: String): Boolean {
        TODO("Not yet implemented")
    }
}