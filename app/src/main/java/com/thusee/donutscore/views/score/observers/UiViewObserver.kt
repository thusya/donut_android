package com.thusee.donutscore.views.score.observers

import androidx.lifecycle.Observer
import com.thusee.donutscore.views.score.ScoreView
import com.thusee.donutscore.views.score.events.ScoreUiViewState

class UiViewObserver(private val scoreView: ScoreView): Observer<ScoreUiViewState> {

    override fun onChanged(stateScore: ScoreUiViewState?) {
        when (stateScore) {
            is ScoreUiViewState.ShowProgressBar -> scoreView.changeUiState(ScoreView.UiState.ShowProgressBar)
            is ScoreUiViewState.HideProgressBar -> scoreView.changeUiState(ScoreView.UiState.HideProgressBar)
            else -> {}
        }
    }
}