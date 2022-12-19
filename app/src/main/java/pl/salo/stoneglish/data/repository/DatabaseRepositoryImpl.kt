package pl.salo.stoneglish.data.repository

import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.model.card.Test
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import pl.salo.stoneglish.domain.services.DatabaseService
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val databaseService: DatabaseService
): DatabaseRepository {
    override suspend fun getUserFlowable(id: String) = databaseService.getUser(id)
    override suspend fun writeUserData(user: User) =
        databaseService.writeUserData(user)

    override suspend fun readCardsList(moduleName: String) =
        databaseService.listOfCards(moduleName)
    override suspend fun readModulesList(): List<String> =
        databaseService.listOfModules()
    override suspend fun readTestsList(moduleName: String): List<Test> =
        databaseService.listOfTests(moduleName)

    override suspend fun writeUserCard(card: Card, module: String) =
        databaseService.writeUserCard(card, module)

}