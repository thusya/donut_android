package com.thusee.donutscore.views.score.observers

import com.thusee.donutscore.usecase.model.ScoreDataMapper
import com.thusee.donutscore.views.score.ScoreView
import com.thusee.donutscore.views.score.events.ScoreLoadState
import io.mockk.CapturingSlot
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ScoreLoadObserverTest {

    val mockView: ScoreView = mockk(relaxed = true)

    lateinit var instance: ScoreLoadObserver

    @BeforeEach
    fun beforeEachTest() {
        instance = ScoreLoadObserver(mockView)
    }

    @AfterEach
    fun afterEachTest() {
        clearAllMocks()
    }

    @DisplayName("onChange(state)")
    @Nested
    inner class OnChangeState {
        @DisplayName("Given state = ScoreLoadState.DisplayData")
        @Nested
        inner class GivenDisplayDataState {
            @DisplayName("then change ScoreView state to display")
            @Test
            fun thenChangeScoreViewStateToDisplayState() {

                val mockData = ScoreDataMapper(currentScore = 500)
                val slot = CapturingSlot<ScoreView.State>()

                instance.onChanged(ScoreLoadState.DisplayData(mockData))

                verify { mockView.changeState(capture(slot)) }
                assertEquals(ScoreView.State.DisplayData::class, slot.captured::class)
                assertEquals(mockData, (slot.captured as ScoreView.State.DisplayData).data)

            }
        }

        @DisplayName("Given state = ScoreLoadState.ErrorHandle")
        @Nested
        inner class GivenErrorHandleState {
            @DisplayName("then change ScoreView state to ErrorHandle state")
            @Test
            fun thenChangeScoreViewStateToErrorHandleState() {

                val slot = CapturingSlot<ScoreView.State>()
                val error = Throwable()

                instance.onChanged(ScoreLoadState.ErrorHandle(error))

                verify { mockView.changeState(capture(slot)) }
                assertEquals(ScoreView.State.ErrorHandle::class, slot.captured::class)
                assertEquals(error, (slot.captured as ScoreView.State.ErrorHandle).e)

            }
        }
    }
}