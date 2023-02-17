package pl.salo.stoneglish.topic.online_use_cases

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
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import pl.salo.stoneglish.domain.use_cases.topic.ReadHorizontalGroupsUseCase
import pl.salo.stoneglish.mock_repositories.MockDatabaseRepository
import pl.salo.stoneglish.util.Utils.same

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class ReadHorizontalGroupsUseCaseTest {
    private val mockData = MockUserData()

    private lateinit var mockDatabase: DatabaseRepository
    private lateinit var useCase: ReadHorizontalGroupsUseCase

    @Before
    fun initSetUp() {
        mockDatabase = MockDatabaseRepository()
        useCase = ReadHorizontalGroupsUseCase(mockDatabase)
    }

    @Test
    fun `get group, pass when count 2 emits`() = runTest {
        val expected = 2

        val actual = useCase.invoke().count()

        assertTrue(
            expected same actual
        )
    }

    @Test
    fun `get group, pass when Loading and Success emit`() = runTest {
        val expected = listOf(
            Resource.Loading(),
            Resource.Success(mockData.topics.mockListOfGroupByType)
        )

        val actual = useCase.invoke().toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }

    @Test
    fun `get group, pass when first Loading and then throws Exception`() = runTest {
        val mockkRep: DatabaseRepository = mockk()
        val mockUseCase = ReadHorizontalGroupsUseCase(mockkRep)

        val expected = listOf<Resource<List<HorizontalGroupByType>>>(
            Resource.Loading(),
            Resource.Error(message = "Test Error")
        )

        coEvery { mockkRep.readHorizontalGroups() } throws HttpException("Test Error")

        val actual = mockUseCase.invoke().toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }
}