package com.digitalcreativity.leagueapplication.di

import com.digitalcreativity.leagueapplication.data.source.remote.ApiGenerator
import org.koin.dsl.module
import retrofit2.Retrofit


val appModule = module {
        single { provideRetrofit() }
    }


private fun provideRetrofit(): Retrofit{
        return ApiGenerator.initRetrofit()
}