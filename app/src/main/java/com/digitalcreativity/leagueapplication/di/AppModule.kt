package com.digitalcreativity.leagueapplication.di

import android.content.Context
import com.digitalcreativity.leagueapplication.data.source.remote.ApiGenerator
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsApi
import com.digitalcreativity.leagueapplication.data.source.remote.teams.TeamsApi
import com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details.TeamDetailsApi
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit


val appModule = module {
        single { provideOkHttpClient() }

        single { provideRetrofit(get()) }

    single {
            createWebService<CompetitionsApi>(retrofit = get())
        }
    single {
        createWebService<CompetitionDetailsApi>(retrofit = get())
    }
    single {
        createWebService<TeamsApi>(retrofit = get())
    }

    single {
        createWebService<TeamDetailsApi>(retrofit = get())
    }

        single { provideNetworkHelper(androidContext()) }
    }


private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return ApiGenerator.initRetrofit(okHttpClient)
}

private fun provideOkHttpClient()=ApiGenerator.addOkHttpClient()

/* function to build our Retrofit service */
inline fun <reified T> createWebService(
    retrofit: Retrofit
): T = retrofit.create(T::class.java)


private fun provideNetworkHelper(context:Context) = NetworkHelper(context)


