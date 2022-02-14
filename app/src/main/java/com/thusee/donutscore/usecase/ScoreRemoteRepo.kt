package com.thusee.donutscore.usecase

import com.thusee.donutscore.data.network.ApiService
import com.thusee.donutscore.data.response.CreditReportInfo
import com.thusee.donutscore.usecase.model.ScoreDataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent

class ScoreRemoteRepo(private val apiService: ApiService): KoinComponent {

    suspend fun fetchRemoteData(): Flow<ScoreDataMapper?> {
        return flow {
            emit(apiService.fetchDonutScore())
        }.flowOn(Dispatchers.IO).map {
            it.creditReportInfo?.let { response ->
                generateScoreData(response)
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