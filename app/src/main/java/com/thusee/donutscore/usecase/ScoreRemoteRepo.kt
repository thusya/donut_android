package com.thusee.donutscore.usecase

import androidx.lifecycle.MutableLiveData
import com.thusee.donutscore.data.network.ApiService
import com.thusee.donutscore.data.response.CreditReportInfo
import com.thusee.donutscore.usecase.model.ScoreDataMapper
import com.thusee.donutscore.views.score.events.ScoreLoadState
import com.thusee.donutscore.views.score.events.ScoreUiViewState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import org.koin.core.component.KoinComponent

class ScoreRemoteRepo(private val apiService: ApiService): KoinComponent {

    suspend fun fetchRemoteData(
        uiViewState: MutableLiveData<ScoreUiViewState>,
        scoreLiveData: MutableLiveData<ScoreLoadState>
    ) {
        flow {
            emit(apiService.fetchDonutScore())
        }.onStart {
            uiViewState.value = ScoreUiViewState.ShowProgressBar
        }.catch { ex ->
            scoreLiveData.value = ScoreLoadState.ErrorHandle(ex)
            uiViewState.value = ScoreUiViewState.HideProgressBar
        }.collect { response ->
            uiViewState.value = ScoreUiViewState.HideProgressBar
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    val creditInfo = data.creditReportInfo
                    scoreLiveData.value =
                        ScoreLoadState.DisplayData(creditInfo?.let { generateScoreData(it) })
                }
            }
        }
    }

    private fun generateScoreData(creditInfo: CreditReportInfo): ScoreDataMapper {
        return ScoreDataMapper(
            currentScore = creditInfo.score,
            totalScore = creditInfo.maxScoreValue,
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
}