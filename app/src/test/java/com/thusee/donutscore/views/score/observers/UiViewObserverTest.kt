package com.thusee.donutscore.views.score.observers

import com.thusee.donutscore.views.score.ScoreView
import com.thusee.donutscore.views.score.events.ScoreUiViewState
import io.mockk.CapturingSlot
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class UiViewObserverTest{

    val mockView: ScoreView = mockk(relaxed = true)

    lateinit var instance: UiViewObserver

    @BeforeEach
    fun beforeEachTest() {
        instance = UiViewObserver(mockView)
    }

    @AfterEach
    fun afterEachTest() {
        clearAllMocks()
    }

    @DisplayName("onChange(state)")
    @Nested
    inner class OnChangeState {
        @DisplayName("Given state = ScoreUiViewState.ShowProgressBar")
        @Nested
        inner class GivenShowProgressBarState {
            @DisplayName("then change ScoreView state to ShowProgressBar")
            @Test
            fun thenChangeScoreViewStateToShowProgressState() {

                val slot = CapturingSlot<ScoreView.UiState>()

                instance.onChanged(ScoreUiViewState.ShowProgressBar)

                verify { mockView.changeUiState(capture(slot)) }
                Assertions.assertEquals(ScoreView.UiState.ShowProgressBar::class, slot.captured::class)

            }
        }

        @DisplayName("Given state = ScoreUiViewState.HideProgressBar")
        @Nested
        inner class GivenHideProgressBarState {
            @DisplayName("then change ScoreView state to HideProgressBar state")
            @Test
            fun thenChangeScoreViewStateToHideProgressBarState() {

                val slot = CapturingSlot<ScoreView.UiState>()

                instance.onChanged(ScoreUiViewState.HideProgressBar)

                verify { mockView.changeUiState(capture(slot)) }
                Assertions.assertEquals(ScoreView.UiState.HideProgressBar::class, slot.captured::class)

            }
        }
    }

}