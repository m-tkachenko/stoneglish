package pl.salo.stoneglish.cards.online_use_cases

import com.bumptech.glide.load.HttpException
import io.mockk.coEvery
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import pl.salo.stoneglish.MockUserData
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.repository.AuthRepository
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import pl.salo.stoneglish.domain.use_cases.cards.ReadListOfModulesUseCase
import pl.salo.stoneglish.mock_repositories.MockAuthRepository
import pl.salo.stoneglish.mock_repositories.MockDatabaseRepository
import pl.salo.stoneglish.util.Utils.same

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class ReadListOfModulesUseCaseTest {
    private val mockData = MockUserData()

    private lateinit var mockDatabase: DatabaseRepository
    private lateinit var mockAuthDatabase: AuthRepository
    private lateinit var useCase: ReadListOfModulesUseCase

    @Before
    fun initSetUp() {
        mockDatabase = MockDatabaseRepository()
        mockAuthDatabase = MockAuthRepository()

        useCase = ReadListOfModulesUseCase(
            mockDatabase, mockAuthDatabase
        )
    }

    @Test
    fun `get cards, pass when count 2 emits`() = runTest {
        val expected = 2

        val actual = useCase.invoke().count()

        assertTrue(
            expected same actual
        )
    }

    @Test
    fun `get cards, pass when Loading and Success emit`() = runTest {
        val expected = listOf(
            Resource.Loading(),
            Resource.Success(mockData.cards.mockModules)
        )

        val actual = useCase.invoke().toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }

    @Test
    fun `get cards empty id, pass when first Loading then Error emits`() = runTest {
        val authMockkRep: AuthRepository = mockk()
        val mockUseCase = ReadListOfModulesUseCase(mockDatabase, authMockkRep)

        val expected = listOf<Resource<List<Card>>>(
            Resource.Loading(),
            Resource.Error(message = "Can't find user")
        )

        coEvery {
            authMockkRep.getUserId()
        } returns ""

        val actual = mockUseCase.invoke().toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }

    @Test
    fun `get cards, pass when first Loading and then throws Exception`() = runTest {
        val mockkRep: DatabaseRepository = mockk()
        val mockUseCase = ReadListOfModulesUseCase(mockkRep, mockAuthDatabase)

        val expected = listOf<Resource<List<Card>>>(
            Resource.Loading(),
            Resource.Error(message = "Test Error")
        )

        coEvery {
            mockkRep.readModulesList(mockData.user.id)
        } throws HttpException("Test Error")

        val actual = mockUseCase.invoke().toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }
}