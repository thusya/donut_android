package com.thusee.donutscore.views.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thusee.donutscore.usecase.ScoreRemoteRepo
import com.thusee.donutscore.views.score.events.ScoreLoadState
import com.thusee.donutscore.views.score.events.ScoreUiViewState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ScoreViewModel: ViewModel(), KoinComponent {
    private val _scoreLiveData = MutableLiveData<ScoreLoadState>()
    val scoreLiveData: LiveData<ScoreLoadState> get() = _scoreLiveData

    private val _uiViewState = MutableLiveData<ScoreUiViewState>()
    val scoreUiViewState: LiveData<ScoreUiViewState> get() = _uiViewState

    private val scoreRemoteRepo: ScoreRemoteRepo by inject()

    init {
        fetchScoreData()
    }

    private fun fetchScoreData() {
        viewModelScope.launch {
            scoreRemoteRepo.fetchRemoteData(_uiViewState, _scoreLiveData)
        }

    }

}