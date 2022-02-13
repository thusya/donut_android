package com.thusee.donutscore.usecase

import com.thusee.donutscore.data.network.ApiService
import com.thusee.donutscore.data.response.ScoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import retrofit2.Response

class ScoreRemoteRepo(private val apiService: ApiService): KoinComponent {

    suspend fun fetchRemoteData(): Flow<Response<ScoreResponse>> {
        return flow {
            emit(apiService.fetchDonutScore())
        }.flowOn(Dispatchers.IO)
    }
}