package com.thusee.donutscore.views.score.events

sealed class ScoreUiViewState{
    object ShowProgressBar: ScoreUiViewState()
    object HideProgressBar: ScoreUiViewState()
}
