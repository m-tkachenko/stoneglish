package pl.salo.stoneglish.data.firebase

import android.util.Log
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.model.card.Test
import pl.salo.stoneglish.domain.services.DatabaseService
import javax.inject.Inject

const val TAG = "DatabaseServiceImpl"

class DatabaseServiceImpl @Inject constructor(
    private val firebaseDatabase: DatabaseReference
) : DatabaseService {
    override suspend fun writeUserData(user: User) {
        firebaseDatabase.child("users").child(user.id).setValue(user).await()
        Log.d(TAG, "writeUserData")
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
        return listOf(
            Card(
                word = "Cat",
                transcription = "|cat|",
                type = "{not a verb}",
                firstTranslation = "1. kot",
                secondTranslation = "2. takie coś jak pies, ale inne",
                thirdTranslation = "3. nie pies"
            ),
            Card(
                word = "Dog",
                transcription = "|dog|",
                type = "{infinitive}",
                firstTranslation = "1. pies",
                secondTranslation = "2. takie coś jak kot, ale inne",
                thirdTranslation = "3. nie kot"
            ),
            Card(
                word = "Jesus Christ",
                transcription = "|Jesus Christ|",
                type = "{savior}",
                firstTranslation = "1. Jezus Chrystus",
                secondTranslation = "2. Zbawiciel",
                thirdTranslation = "3. Bóg"
            )
        )
    }

    override suspend fun listOfModules(): List<String> {
        return listOf(
            "Food",
            "Religion",
            "Work",
            "TravellingWithLessStress"
        )
    }

    override suspend fun listOfTests(): List<Test> {
        return listOf(
            Test(
                name = "Art",
                description = "Very very very important questions about important food"
            ),
            Test(
                name = "Countries",
                description = "Yes, yes, this is good one"
            )
        )
    }
}