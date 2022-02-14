package com.thusee.donutscore.usecase

import com.thusee.donutscore.TestInjector
import com.thusee.donutscore.data.network.ApiService
import com.thusee.donutscore.data.response.CreditReportInfo
import com.thusee.donutscore.data.response.ScoreResponse
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import kotlin.test.assertEquals

internal class ScoreRemoteRepoTest {

    private val testInjector = TestInjector()
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
                val mockResponse= ScoreResponse(creditReportInfo = CreditReportInfo(score = 200, maxScoreValue = 500))

                coEvery { mockApiService.fetchDonutScore() } returns mockResponse

                runBlocking {
                    val result = instance.fetchRemoteData()

                    assertEquals(200, result.first()?.currentScore)
                }

                coVerify { mockApiService.fetchDonutScore() }
            }
        }
    }

}