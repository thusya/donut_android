package com.thusee.donutscore.views.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thusee.donutscore.usecase.model.ScoreDataMapper

interface ScoreView {

    fun inflate(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    fun changeUiState(viewState: UiState)
    fun changeState(state: State)
    fun dispose()
    fun updateData(data: ScoreDataMapper?)
    fun setCallBack(listener: ScoreClickListener)
    fun getScoreDate(): ScoreDataMapper

    sealed class State{
        data class DisplayData(val data: ScoreDataMapper?): State()
        data class ErrorHandle(val e: Throwable): State()
    }

    sealed class UiState {
        object ShowProgressBar: UiState()
        object HideProgressBar: UiState()
    }
}