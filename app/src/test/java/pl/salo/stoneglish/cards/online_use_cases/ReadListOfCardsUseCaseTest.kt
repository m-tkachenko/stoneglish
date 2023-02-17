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
import pl.salo.stoneglish.domain.use_cases.cards.ReadListOfCardsUseCase
import pl.salo.stoneglish.mock_repositories.MockAuthRepository
import pl.salo.stoneglish.mock_repositories.MockDatabaseRepository
import pl.salo.stoneglish.util.Utils.same

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class ReadListOfCardsUseCaseTest {
    private val mockData = MockUserData()

    private lateinit var mockDatabase: DatabaseRepository
    private lateinit var mockAuthDatabase: AuthRepository
    private lateinit var useCase: ReadListOfCardsUseCase

    @Before
    fun initSetUp() {
        mockDatabase = MockDatabaseRepository()
        mockAuthDatabase = MockAuthRepository()

        useCase = ReadListOfCardsUseCase(
            mockDatabase, mockAuthDatabase
        )
    }

    @Test
    fun `get cards, pass when count 2 emits`() = runTest {
        val expected = 2

        val actual = useCase.invoke(
            moduleName = "Test Module"
        ).count()

        assertTrue(
            expected same actual
        )
    }

    @Test
    fun `get cards, pass when Loading and Success emit`() = runTest {
        val expected = listOf(
            Resource.Loading(),
            Resource.Success(mockData.cards.mockCards)
        )

        val actual = useCase.invoke(moduleName = "Test Module").toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }

    @Test
    fun `get cards empty cards list, pass when first Loading then Error emits`() = runTest {
        val mockkRep: DatabaseRepository = mockk()
        val mockUseCase = ReadListOfCardsUseCase(mockkRep, mockAuthDatabase)

        val expected = listOf<Resource<List<Card>>>(
            Resource.Loading(),
            Resource.Error(message = "We don't see any cards :(")
        )

        coEvery { mockkRep.readCardsList(
            moduleName = "Test Module Error",
            userId = mockData.user.id
        ) } returns listOf()

        val actual = mockUseCase.invoke("Test Module Error").toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }

    @Test
    fun `get cards empty id, pass when first Loading then Error emits`() = runTest {
        val mockkRep: DatabaseRepository = mockk()
        val authMockkRep: AuthRepository = mockk()

        val mockUseCase = ReadListOfCardsUseCase(mockkRep, authMockkRep)

        val expected = listOf<Resource<List<Card>>>(
            Resource.Loading(),
            Resource.Error(message = "We don't see any cards :(")
        )

        coEvery {
            authMockkRep.getUserId()
        } returns ""

        coEvery { mockkRep.readCardsList(
            moduleName = "Test Module Error Id",
            userId = ""
        ) } returns listOf()

        val actual = mockUseCase.invoke("Test Module Error Id").toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }

    @Test
    fun `get cards, pass when first Loading and then throws Exception`() = runTest {
        val mockkRep: DatabaseRepository = mockk()
        val mockUseCase = ReadListOfCardsUseCase(mockkRep, mockAuthDatabase)

        val expected = listOf<Resource<List<Card>>>(
            Resource.Loading(),
            Resource.Error(message = "Test Error")
        )

        coEvery {
            mockkRep.readCardsList("Test Module", mockData.user.id)

        } throws HttpException("Test Error")

        val actual = mockUseCase.invoke(moduleName = "Test Module").toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }
}