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
import pl.salo.stoneglish.domain.repository.DatabaseRepository
import pl.salo.stoneglish.domain.use_cases.topic.WriteNewTopicUseCase
import pl.salo.stoneglish.util.Utils.same

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class WriteNewTopicUseCaseTest {
    private val mockData = MockUserData().topics

    private lateinit var mockDatabase: DatabaseRepository
    private lateinit var useCase: WriteNewTopicUseCase

    @Before
    fun initSetUp() {
        mockDatabase = mockk()
        useCase = WriteNewTopicUseCase(mockDatabase)
    }

    @Test
    fun `get topics, pass when count 2 emits`() = runTest {
        val expected = 2

        coEvery {
            mockDatabase.writeNewTopic(mockData.mockListOfTopicArtType.first())
        } returns Unit

        val actual = useCase.invoke(
            topic = mockData.mockListOfTopicArtType.first()
        ).count()

        assertTrue(
            expected same actual
        )
    }

    @Test
    fun `get topics, pass when Loading and Success emit`() = runTest {
        val expected = listOf(
            Resource.Loading(),
            Resource.Success(data = true)
        )

        coEvery {
            mockDatabase.writeNewTopic(mockData.mockListOfTopicArtType.first())
        } returns Unit

        val actual = useCase.invoke(
            topic = mockData.mockListOfTopicArtType.first()
        ).toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }

    @Test
    fun `get topics, pass when first Loading emit and then throws Exception`() = runTest {
        val expected = listOf<Resource<Boolean>>(
            Resource.Loading(),
            Resource.Error(message = "Test Error")
        )

        coEvery {
            mockDatabase.writeNewTopic(mockData.mockListOfTopicArtType.first())
        } throws HttpException("Test Error")

        val actual = useCase.invoke(
            topic = mockData.mockListOfTopicArtType.first()
        ).toList()

        assertThat(expected)
            .usingRecursiveComparison()
            .isEqualTo(actual)
    }
}