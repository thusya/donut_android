package com.thusee.donutscore.data.network

import com.thusee.donutscore.data.response.ScoreResponse
import retrofit2.http.GET

interface ApiService {

    @GET("endpoint.json")
    suspend fun fetchDonutScore(): ScoreResponse
}