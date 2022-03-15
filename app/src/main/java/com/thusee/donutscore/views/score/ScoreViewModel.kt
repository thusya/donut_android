package com.thusee.donutscore.views.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thusee.donutscore.usecase.ScoreRemoteRepo
import com.thusee.donutscore.views.score.events.ScoreLoadState
import com.thusee.donutscore.views.score.events.ScoreUiViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ScoreViewModel(private val scoreRemoteRepo: ScoreRemoteRepo): ViewModel(), KoinComponent {
    private val _scoreLiveData = MutableLiveData<ScoreLoadState>()
    val scoreLiveData: LiveData<ScoreLoadState> get() = _scoreLiveData

    private val _uiViewState = MutableLiveData<ScoreUiViewState>()
    val scoreUiViewState: LiveData<ScoreUiViewState> get() = _uiViewState

    init {
        fetchScoreData()
    }

    fun fetchScoreData() {
        viewModelScope.launch(Dispatchers.Main) {
            scoreRemoteRepo.fetchRemoteData().onStart {
                _uiViewState.postValue(ScoreUiViewState.ShowProgressBar)
            }.catch { ex ->
                _scoreLiveData.postValue(ScoreLoadState.ErrorHandle(ex))
                _uiViewState.postValue(ScoreUiViewState.HideProgressBar)
            }.collect { response ->
                _uiViewState.postValue(ScoreUiViewState.HideProgressBar)

                _scoreLiveData.postValue(ScoreLoadState.DisplayData(response))
            }

        }
    }
}
