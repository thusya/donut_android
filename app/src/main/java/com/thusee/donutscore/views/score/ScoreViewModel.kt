package com.thusee.donutscore.views.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thusee.donutscore.data.response.CreditReportInfo
import com.thusee.donutscore.usecase.ScoreRemoteRepo
import com.thusee.donutscore.usecase.model.ScoreDataMapper
import com.thusee.donutscore.views.score.events.ScoreLoadState
import com.thusee.donutscore.views.score.events.ScoreUiViewState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ScoreViewModel: ViewModel(), KoinComponent {
    private val _scoreLiveData = MutableLiveData<ScoreLoadState>()
    val scoreLiveData: LiveData<ScoreLoadState> get() = _scoreLiveData

    private val _uiViewState = MutableLiveData<ScoreUiViewState>()
    val scoreUiViewState: LiveData<ScoreUiViewState> get() = _uiViewState
    private var scoreData: ScoreDataMapper? = ScoreDataMapper()

    private val scoreRemoteRepo: ScoreRemoteRepo by inject()

    init {
        fetchScoreData()
    }

    private fun fetchScoreData() {
        viewModelScope.launch {
            scoreRemoteRepo.fetchRemoteData()
                .onStart {
                    _uiViewState.value = ScoreUiViewState.ShowProgressBar
                }.catch { ex ->
                    _scoreLiveData.value = ScoreLoadState.ErrorHandle(ex)
                    _uiViewState.value = ScoreUiViewState.HideProgressBar
                }
                .collect { response ->
                    _uiViewState.value = ScoreUiViewState.HideProgressBar
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            val creditInfo = data.creditReportInfo
                            _scoreLiveData.value =
                                ScoreLoadState.DisplayData(creditInfo?.let { getScoreData(it) })
                            scoreData = creditInfo?.let { getScoreData(it) }
                        }
                    }
                }
        }
    }

    private fun getScoreData(creditInfo: CreditReportInfo): ScoreDataMapper {
        return ScoreDataMapper(
            currentScore = creditInfo.score, totalScore = creditInfo.maxScoreValue,
            scoreBand = creditInfo.scoreBand,
            clientRef = creditInfo.clientRef,
            currentShortTermDebt = creditInfo.currentShortTermDebt,
            currentShortTermNonPromotionalDebt = creditInfo.currentShortTermNonPromotionalDebt,
            currentShortTermCreditUtilisation = creditInfo.currentShortTermCreditUtilisation,
            currentShortTermCreditLimit = creditInfo.currentShortTermCreditLimit,
            changeInShortTermDebt = creditInfo.changeInShortTermDebt,
            currentLongTermDebt = creditInfo.currentLongTermDebt,
            currentLongTermNonPromotionalDebt = creditInfo.currentLongTermNonPromotionalDebt
        )
    }

    fun getScoreDate() = scoreData

}