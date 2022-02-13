package com.thusee.donutscore.views.score.observers

import androidx.lifecycle.Observer
import com.thusee.donutscore.views.score.ScoreView
import com.thusee.donutscore.views.score.events.ScoreLoadState
import com.thusee.donutscore.views.score.events.ScoreUiViewState

class ScoreLoadObserver(private val scoreView: ScoreView): Observer<ScoreLoadState> {

    override fun onChanged(state: ScoreLoadState?) {
        when (state) {
            is ScoreLoadState.DisplayData -> scoreView.changeState(ScoreView.State.DisplayData(state.data))
            is ScoreLoadState.ErrorHandle -> scoreView.changeState(
                ScoreView.State.ErrorHandle(
                    state.e
                )
            )
            else -> {}
        }
    }
}