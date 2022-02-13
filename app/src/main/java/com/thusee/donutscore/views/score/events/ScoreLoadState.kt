package com.thusee.donutscore.views.score.events

import com.thusee.donutscore.usecase.model.ScoreDataMapper

sealed class ScoreLoadState{
    data class DisplayData(var data: ScoreDataMapper?): ScoreLoadState()
    data class ErrorHandle(val e: Throwable) : ScoreLoadState()
}

