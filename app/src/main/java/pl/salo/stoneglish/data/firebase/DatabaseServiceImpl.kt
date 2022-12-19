package pl.salo.stoneglish.data.firebase

import android.util.Log
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.model.card.Test
import pl.salo.stoneglish.domain.services.DatabaseService
import javax.inject.Inject

const val TAG = "FirebaseDatabaseService"

class DatabaseServiceImpl @Inject constructor(
    private val firebaseDatabase: DatabaseReference
) : DatabaseService {
    override suspend fun writeUserData(user: User) {
        firebaseDatabase.child("users").child(user.id).setValue(user).await()
        Log.d(TAG, "writeUserData")
    }

    override suspend fun getUser(id: String): User {
        val result =
            firebaseDatabase.child("users").child(id).get().addOnCompleteListener {}.await()
        return result.getValue(User::class.java) ?: throw Exception("No such user")
    }

    override suspend fun writeUserCard(card: Card, module: String) {
        firebaseDatabase
            .child("users")
            .child("Y1CQmGnx8IdMIjTEwKdbVhXRiPl2")
            .child("cards")
            .child(module)
            .child(card.word)

            .setValue(card)
            .await()

        Log.d(TAG, "writeUserCard")
    }

    override suspend fun listOfCards(moduleName: String): List<Card> {
        val resultCardsSnapshot = firebaseDatabase
            .child("users")
            .child("Y1CQmGnx8IdMIjTEwKdbVhXRiPl2")
            .child("cards")
            .child(moduleName)
            .get()
            .await()

        val cardList: MutableList<Card> = resultCardsSnapshot.children.map { snap ->
            snap.getValue(Card::class.java)!!
        } as MutableList<Card>

        cardList.remove(Card())
        Log.d(TAG, "Those cards were received: $cardList")

        return cardList
    }

    override suspend fun listOfModules(): List<String> {
        val moduleList: MutableList<String> = mutableListOf()

        firebaseDatabase
            .child("users")
            .child("Y1CQmGnx8IdMIjTEwKdbVhXRiPl2")
            .child("cards")
            .get()
            .await()
            .children.forEach { snap ->
                moduleList.add(snap.key ?: "")
            }

        Log.d(TAG, "Those modules were received: $moduleList")
        return moduleList
    }

    override suspend fun listOfTests(moduleName: String): List<Test> {
        val resultTestsSnapshot = firebaseDatabase
            .child("users")
            .child("Y1CQmGnx8IdMIjTEwKdbVhXRiPl2")
            .child("cards")
            .child(moduleName)
            .child("tests")
            .get()
            .await()

        val testList: List<Test> = resultTestsSnapshot.children.map { snap ->
            snap.getValue(Test::class.java) ?: Test()
        }

        Log.d(TAG, "Those tests were received: $testList")
        return testList
    }
}