package com.thusee.donutscore.views.score

import androidx.lifecycle.MutableLiveData
import com.thusee.donutscore.MainCoroutineRule
import com.thusee.donutscore.TestInjector
import com.thusee.donutscore.TestUtils
import com.thusee.donutscore.usecase.ScoreRemoteRepo
import com.thusee.donutscore.usecase.model.ScoreDataMapper
import com.thusee.donutscore.views.score.events.ScoreLoadState
import com.thusee.donutscore.views.score.events.ScoreUiViewState
import io.mockk.CapturingSlot
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
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

    val scoreDataMapper = ScoreDataMapper(scoreBand = 600, currentScore = 200)

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

        // coEvery { mockFlow.onStart(any()) } returns mockFlow
//        coEvery { mockFlow.catch(any()) } returns flow{
//            emit(scoreDataMapper)
//        }

        instance = ScoreViewModel()
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

//    @DisplayName("collect")
//    @Nested
//    inner class Collect {
//        @DisplayName("then get data")
//        @Test
//        fun thenGetData() {
//            val mockUiViewState: MutableLiveData<ScoreUiViewState> = mockk(relaxed = true)
//            val mockScoreLiveData: MutableLiveData<ScoreLoadState> = mockk(relaxed = true)
//
//            val slotData = CapturingSlot<ScoreLoadState.DisplayData>()
//            val slotProgress = CapturingSlot<ScoreUiViewState.HideProgressBar>()
//
//            runBlocking {
//                instance.fetchScoreData()
//
//                coVerify { mockScoreLiveData.postValue(capture(slotData)) }
//                coVerify { mockUiViewState.postValue(capture(slotProgress)) }
//
//                assertEquals(ScoreLoadState.DisplayData::class, slotData.captured::class)
//                assertEquals(ScoreUiViewState.HideProgressBar::class, slotProgress.captured::class)
//            }
//
//        }
//    }
//
//    @DisplayName("throws error")
//    @Nested
//    inner class ThrowsError {
//        @DisplayName("then show error state")
//        @Test
//        fun thenShowErrorState() {
//            val mockUiViewState: MutableLiveData<ScoreUiViewState> = mockk(relaxed = true)
//            val mockException: Exception = mockk(relaxed = true)
//            val mockScoreLiveData: MutableLiveData<ScoreLoadState> = mockk(relaxed = true)
//
//            val slotData = CapturingSlot<ScoreLoadState.ErrorHandle>()
//
//            coEvery { mockRepo.fetchRemoteData() } throws mockException
//
//            runBlocking { instance.fetchScoreData() }
//
//            verify { mockScoreLiveData.value = capture(slotData) }
//
//            assertEquals(ScoreLoadState.ErrorHandle::class, slotData.captured::class)
//        }
//    }

}
