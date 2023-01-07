package com.digitalcreativity.leagueapplication.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val localDBModule = module {
    single { provideLeagueDatabase(androidContext()) }
    single { provideCompetitionsDao(get()) }
    single { provideTeamsDao(get()) }
}

private fun provideLeagueDatabase(context: Context) =
    Room.databaseBuilder(context, LeagueDatabase::class.java, "league_database")
        .fallbackToDestructiveMigration()
        .build()

private fun provideCompetitionsDao(leagueDb:LeagueDatabase)
= leagueDb.competitionsDao()

private fun provideTeamsDao(leagueDb:LeagueDatabase)
        = leagueDb.teamsDao()