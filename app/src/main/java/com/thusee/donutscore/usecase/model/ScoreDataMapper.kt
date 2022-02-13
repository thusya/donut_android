package com.thusee.donutscore.usecase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScoreDataMapper(
val currentScore: Int?  = 0,
val totalScore: Int? = 0,
val scoreBand:Int?  = 0,
val clientRef:String?  = "",
val currentShortTermDebt:Int? = 0,
val currentShortTermNonPromotionalDebt:Int? = 0,
val currentShortTermCreditLimit:Int? = 0,
val currentShortTermCreditUtilisation:Int?=0,
val changeInShortTermDebt:Int?=0,
val currentLongTermDebt:Int? =0,
val currentLongTermNonPromotionalDebt:Int?=0
): Parcelable {
    fun getPercentage(): Float {
        if (currentScore == null || totalScore == null) return 0f
        return (currentScore.toFloat() / totalScore.toFloat()) * 100
    }
}
