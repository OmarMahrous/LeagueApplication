package com.digitalcreativity.leagueapplication.di

import android.content.Context
import com.digitalcreativity.leagueapplication.data.source.remote.ApiGenerator
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit


val appModule = module {
        single { provideOkHttpClient() }

        single { provideRetrofit(get()) }

        single { provideNetworkHelper(androidContext()) }
    }


private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return ApiGenerator.initRetrofit(okHttpClient)
}

private fun provideOkHttpClient()=ApiGenerator.addOkHttpClient()

private fun provideNetworkHelper(context:Context) = NetworkHelper(context)