package com.thusee.donutscore.di

import com.thusee.donutscore.BuildConfig
import com.thusee.donutscore.data.network.ApiService
import com.thusee.donutscore.usecase.ScoreRemoteRepo
import com.thusee.donutscore.views.score.ScoreView
import com.thusee.donutscore.views.score.ScoreViewModel
import com.thusee.donutscore.views.score.impl.ScoreViewImpl
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    viewModel { ScoreViewModel(get()) }

    single<ScoreView> { ScoreViewImpl() }

    single<ApiService> {

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(ApiService::class.java)
    }

    factory { ScoreRemoteRepo(get()) }

}