package pl.salo.stoneglish.data.firebase

import android.util.Log
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.services.DatabaseService
import javax.inject.Inject

const val TAG = "DatabaseServiceImpl"

class DatabaseServiceImpl @Inject constructor(private val firebaseDatabase: DatabaseReference) :
    DatabaseService {
    override suspend fun writeUserData(user: User) {
        firebaseDatabase.child("users").child(user.id).setValue(user).await()
        Log.d(TAG, "writeUserData")
    }

}