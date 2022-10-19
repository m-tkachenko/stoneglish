package pl.salo.stoneglish.data.repository

import pl.salo.stoneglish.data.model.User
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import pl.salo.stoneglish.domain.services.DatabaseService
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val databaseService: DatabaseService
) : DatabaseRepository {
    override suspend fun writeUserData(user: User) = databaseService.writeUserData(user)
}