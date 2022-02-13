package com.thusee.donutscore.data.response

import com.squareup.moshi.Json

data class ScoreResponse(
    @field:Json(name = "accountIDVStatus") var accountIDVStatus: String? = "",
    @field:Json(name = "creditReportInfo") var creditReportInfo: CreditReportInfo? = CreditReportInfo(),
    @field:Json(name = "dashboardStatus") var dashboardStatus: String? = "",
    @field:Json(name = "personaType") var personaType: String? = "",
    @field:Json(name = "coachingSummary") var coachingSummary: CoachingSummary? = CoachingSummary(),
    @field:Json(name = "augmentedCreditScore") var augmentedCreditScore: String? = ""
)

data class CreditReportInfo(
    @field:Json(name = "score") var score: Int? = 0,
    @field:Json(name = "scoreBand") var scoreBand: Int? = 0,
    @field:Json(name = "clientRef") var clientRef: String? = "",
    @field:Json(name = "status") var status: String? = "",
    @field:Json(name = "maxScoreValue") var maxScoreValue: Int? = 0,
    @field:Json(name = "minScoreValue") var minScoreValue: Int? = 0,
    @field:Json(name = "monthsSinceLastDefaulted") var monthsSinceLastDefaulted: Int? = 0,
    @field:Json(name = "hasEverDefaulted") var hasEverDefaulted: Boolean? = false,
    @field:Json(name = "monthsSinceLastDelinquent") var monthsSinceLastDelinquent: Int? = 0,
    @field:Json(name = "hasEverBeenDelinquent") var hasEverBeenDelinquent: Boolean? = false,
    @field:Json(name = "percentageCreditUsed") var percentageCreditUsed: Int? = 0,
    @field:Json(name = "percentageCreditUsedDirectionFlag") var percentageCreditUsedDirectionFlag: Int? = 0,
    @field:Json(name = "changedScore") var changedScore: Int? = 0,
    @field:Json(name = "currentShortTermDebt") var currentShortTermDebt: Int? = 0,
    @field:Json(name = "currentShortTermNonPromotionalDebt") var currentShortTermNonPromotionalDebt: Int? = 0,
    @field:Json(name = "currentShortTermCreditLimit") var currentShortTermCreditLimit: Int? = 0,
    @field:Json(name = "currentShortTermCreditUtilisation") var currentShortTermCreditUtilisation: Int? = 0,
    @field:Json(name = "changeInShortTermDebt") var changeInShortTermDebt: Int? = 0,
    @field:Json(name = "currentLongTermDebt") var currentLongTermDebt: Int? = 0,
    @field:Json(name = "currentLongTermNonPromotionalDebt") var currentLongTermNonPromotionalDebt: Int? = 0,
    @field:Json(name = "currentLongTermCreditLimit") var currentLongTermCreditLimit: String? = "",
    @field:Json(name = "currentLongTermCreditUtilisation") var currentLongTermCreditUtilisation: String? = "",
    @field:Json(name = "changeInLongTermDebt") var changeInLongTermDebt: Int? = 0,
    @field:Json(name = "numPositiveScoreFactors") var numPositiveScoreFactors: Int? = 0,
    @field:Json(name = "numNegativeScoreFactors") var numNegativeScoreFactors: Int? = 0,
    @field:Json(name = "equifaxScoreBand") var equifaxScoreBand: Int? = 0,
    @field:Json(name = "equifaxScoreBandDescription") var equifaxScoreBandDescription: String? = "",
    @field:Json(name = "daysUntilNextReport") var daysUntilNextReport: Int? = 0
)

data class CoachingSummary(
    @field:Json(name = "activeTodo") var activeTodo: Boolean? = false,
    @field:Json(name = "activeChat") var activeChat: Boolean? = false,
    @field:Json(name = "numberOfTodoItems") var numberOfTodoItems: Int? = 0,
    @field:Json(name = "numberOfCompletedTodoItems") var numberOfCompletedTodoItems: Int? = 0,
    @field:Json(name = "selected") var selected: Boolean? = false
)
