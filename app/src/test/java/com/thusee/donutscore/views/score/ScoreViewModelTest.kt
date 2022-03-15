package com.thusee.donutscore.views.score

import androidx.lifecycle.MutableLiveData
import com.thusee.donutscore.MainCoroutineRule
import com.thusee.donutscore.TestInjector
import com.thusee.donutscore.TestUtils
import com.thusee.donutscore.usecase.ScoreRemoteRepo
import com.thusee.donutscore.usecase.model.ScoreDataMapper
import com.thusee.donutscore.views.score.events.ScoreLoadState
import com.thusee.donutscore.views.score.events.ScoreUiViewState
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@ExtendWith(MainCoroutineRule::class)
@TestInstance(PER_CLASS)
internal class ScoreViewModelTest {

    private val testInjector = TestInjector()
    val mockRepo: ScoreRemoteRepo = mockk(relaxed = true)
    val mockUiViewState: MutableLiveData<ScoreUiViewState> = mockk(relaxed = true)
    val mockScoreLiveData: MutableLiveData<ScoreLoadState> = mockk(relaxed = true)

    val mockFlow: Flow<ScoreDataMapper> = mockk(relaxed = true)

    lateinit var instance: ScoreViewModel

    @BeforeAll
    fun beforeAll() {
        testInjector.start(module {
            factory { mockRepo }
        })
    }

    @BeforeEach
    fun beforeEachTest() {

        coEvery { mockRepo.fetchRemoteData() } returns mockFlow

        instance = ScoreViewModel(mockRepo)
        TestUtils.setProperty(instance, "_scoreLiveData", mockScoreLiveData)
        TestUtils.setProperty(instance, "_uiViewState", mockUiViewState)

    }

    @AfterEach
    fun afterEachTest() {
        clearAllMocks()
        unmockkAll()
    }

    @DisplayName("init")
    @Nested
    inner class InitViewModel {
        @DisplayName("then fetch data from repo")
        @Test
        fun thenFetchDataFromRepo() {

            coVerify {
                mockRepo.fetchRemoteData()
            }
        }
    }

    @DisplayName("fetch score data")
    @Nested
    inner class FetchScoreData {
        @DisplayName("then fetch data from repo")
        @Test
        fun thenFetchDataFromRepo() {
            instance.fetchScoreData()
            coVerify {
                mockRepo.fetchRemoteData()
            }
        }

    }

    @DisplayName("collect")
    @Nested
    inner class Collect {
        @DisplayName("then get data")
        @Test
        fun thenGetData() {
            val slotData = CapturingSlot<ScoreLoadState.DisplayData>()
            val mockScoreDataMapper: ScoreDataMapper = mockk(relaxed = true)

            coEvery { mockRepo.fetchRemoteData() } returns flowOf(mockScoreDataMapper)

            runBlockingTest {
                instance.fetchScoreData()

                coVerify { mockUiViewState.postValue(any<ScoreUiViewState.HideProgressBar>()) }
                coVerify { mockScoreLiveData.postValue(capture(slotData)) }

                assertEquals(ScoreLoadState.DisplayData::class, slotData.captured::class)
                assertEquals(mockScoreDataMapper, slotData.captured.data)
            }

        }
    }

    @DisplayName("throws error")
    @Nested
    inner class ThrowsError {
        @DisplayName("then show error state")
        @Test
        fun thenShowErrorState() {
            val slotData = CapturingSlot<ScoreLoadState.ErrorHandle>()
            val mockThrowable: Throwable = mockk(relaxed = true)

            coEvery { mockRepo.fetchRemoteData() } returns flow {
                throw mockThrowable
            }

            runBlockingTest {
                instance.fetchScoreData()

                coVerify { mockScoreLiveData.postValue(capture(slotData)) }
                assertEquals(mockThrowable, slotData.captured.e)

                coVerify { mockUiViewState.postValue(any<ScoreUiViewState.HideProgressBar>()) }
            }
        }
    }

}