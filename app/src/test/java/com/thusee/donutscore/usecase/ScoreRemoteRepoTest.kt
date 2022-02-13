package com.thusee.donutscore.usecase

import androidx.lifecycle.MutableLiveData
import com.thusee.donutscore.TestInjector
import com.thusee.donutscore.data.network.ApiService
import com.thusee.donutscore.data.response.CreditReportInfo
import com.thusee.donutscore.data.response.ScoreResponse
import com.thusee.donutscore.views.score.events.ScoreLoadState
import com.thusee.donutscore.views.score.events.ScoreUiViewState
import io.mockk.CapturingSlot
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import retrofit2.Response
import java.lang.Exception
import kotlin.test.assertEquals

internal class ScoreRemoteRepoTest {

    val testInjector = TestInjector()
    val mockApiService: ApiService = mockk(relaxed = true)

    lateinit var instance: ScoreRemoteRepo

    @BeforeEach
    fun beforeEachTest() {
        testInjector.start(module {
            factory { mockApiService }
        })

        instance = ScoreRemoteRepo(mockApiService)
    }

    @AfterEach
    fun afterEachTest() {
        testInjector.stop()
        clearAllMocks()
        unmockkAll()
    }

    @DisplayName("fetchRemoteData")
    @Nested
    inner class FetchRemoteData {
        @DisplayName("given data, livedata state")
        @Nested
        inner class GivenDataLiveDataState {
            @DisplayName("call the api")
            @Test
            fun calApiService() {
                val mockUiViewState: MutableLiveData<ScoreUiViewState> = mockk(relaxed = true)
                val mockResponse: Response<ScoreResponse> = mockk(relaxed = true)
                val mockScoreLiveData: MutableLiveData<ScoreLoadState> = mockk(relaxed = true)

                coEvery { mockApiService.fetchDonutScore() } returns mockResponse

                runBlocking() {
                    instance.fetchRemoteData(mockUiViewState, mockScoreLiveData)
                }

                coVerify { mockApiService.fetchDonutScore() }
            }
        }

        @DisplayName("throws error")
        @Nested
        inner class ThrowsError {
            @DisplayName("then show error state")
            @Test
            fun thenShowErrorState() {
                val mockUiViewState: MutableLiveData<ScoreUiViewState> = mockk(relaxed = true)
                val mockException: Exception = mockk(relaxed = true)
                val mockScoreLiveData: MutableLiveData<ScoreLoadState> = mockk(relaxed = true)

                val slotData = CapturingSlot<ScoreLoadState.ErrorHandle>()

                coEvery { mockApiService.fetchDonutScore() } throws mockException

                runBlocking { instance.fetchRemoteData(mockUiViewState, mockScoreLiveData) }

                verify { mockScoreLiveData.value = capture(slotData) }

                assertEquals(ScoreLoadState.ErrorHandle::class, slotData.captured::class)
            }
        }

//        @DisplayName("collect")
//        @Nested
//        inner class Collect {
//            @DisplayName("then get data")
//            @Test
//            fun thenGetData() {
//                val mockUiViewState: MutableLiveData<ScoreUiViewState> = mockk(relaxed = true)
//                val mockResponse: Response<ScoreResponse> = mockk(relaxed = true)
//                val mockScoreLiveData: MutableLiveData<ScoreLoadState> = mockk(relaxed = true)
//                val mockData = ScoreResponse(creditReportInfo = CreditReportInfo(score = 700))
//
//                val slotData = CapturingSlot<ScoreLoadState.DisplayData>()
//
//                coEvery { mockApiService.fetchDonutScore() } returns mockResponse
//                every { mockResponse.body() } returns mockData
//
//                runBlocking { instance.fetchRemoteData(mockUiViewState, mockScoreLiveData) }
//                verify { mockScoreLiveData.value = capture(slotData) }
//
//                assertEquals(ScoreLoadState.DisplayData::class, slotData.captured::class)
//
//            }
//        }

    }

}