package com.thusee.donutscore.views.score

import androidx.lifecycle.MutableLiveData
import com.thusee.donutscore.MainCoroutineRule
import com.thusee.donutscore.TestInjector
import com.thusee.donutscore.TestUtils
import com.thusee.donutscore.usecase.ScoreRemoteRepo
import com.thusee.donutscore.views.score.events.ScoreLoadState
import com.thusee.donutscore.views.score.events.ScoreUiViewState
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@ExtendWith(MainCoroutineRule::class)
internal class ScoreViewModelTest {

    val testInjector = TestInjector()
    val mockRepo: ScoreRemoteRepo = mockk(relaxed = true)
    val mockViewModelScope: CoroutineScope = mockk(relaxed = true)
    val mockUiViewState: MutableLiveData<ScoreUiViewState> = mockk(relaxed = true)
    val mockScoreLiveData: MutableLiveData<ScoreLoadState> = mockk(relaxed = true)

    lateinit var instance: ScoreViewModel

    @BeforeEach
    fun beforeEachTest() {
        testInjector.start(module {
            factory { mockRepo }
        })

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
//            every { instance.viewModelScope } returns mockViewModelScope
//
//            verify {
//                runBlocking {
//                    mockRepo.fetchRemoteData(mockUiViewState, mockScoreLiveData)
//                }
//            }
        }

    }
}