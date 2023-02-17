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
import pl.salo.stoneglish.data.model.home.HorizontalGroupByType
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import pl.salo.stoneglish.domain.use_cases.home.ReadListOfDailyCardsUseCase
import pl.salo.stoneglish.domain.use_cases.topic.ReadHorizontalGroupsUseCase
import pl.salo.stoneglish.mock_repositories.MockDatabaseRepository
import pl.salo.stoneglish.util.Utils.same

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class ReadListOfDailyCardsUseCaseTest {
    private val mockData = MockUserData()

    private lateinit var mockDatabase: DatabaseRepository
    private lateinit var useCase: ReadListOfDailyCardsUseCase

    @Before
    fun initSetUp() {
        mockDatabase = MockDatabaseRepository()
        useCase = ReadListOfDailyCardsUseCase(mockDatabase)
    }

    @Test
    fun `get daily cards, pass when count 2 emits`() = runTest {
        val expected = 2

        val actual = useCase.invoke().count()

        assertTrue(
            expected same actual
        )
    }

    @Test
    fun `get daily cards, pass when Loading and Success emit`() = runTest {
        val expected = listOf(
            Resource.Loading(),
            Resource.Success(mockData.cards.mockCards)
        )

        val actual = useCase.invoke().toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }

    @Test
    fun `get daily cards, pass when first Loading and then throws Exception`() = runTest {
        val mockkRep: DatabaseRepository = mockk()
        val mockUseCase = ReadListOfDailyCardsUseCase(mockkRep)

        val expected = listOf<Resource<List<Card>>>(
            Resource.Loading(),
            Resource.Error(message = "Test Error")
        )

        coEvery { mockkRep.readListOfDailyCards() } throws HttpException("Test Error")

        val actual = mockUseCase.invoke().toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }
}