package com.digitalcreativity.leagueapplication.data.repository.teams

import android.util.Log
import com.digitalcreativity.leagueapplication.LeagueApp
import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.local.teams.TeamsDao
import com.digitalcreativity.leagueapplication.data.source.local.teams.TeamsLocalSource
import com.digitalcreativity.leagueapplication.data.source.remote.teams.TeamsApi
import com.digitalcreativity.leagueapplication.data.source.remote.teams.TeamsRemoteSource
import com.digitalcreativity.leagueapplication.data.util.Resource
import com.digitalcreativity.leagueapplication.data.util.Status
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

class TeamsRepositoryImpl : TeamsRepository {

    private val TAG = "TeamsRepositoryImpl"


    private val defaultComptId=2000


    private val remoteDataSource: TeamsRemoteSource
    private val localDataSource: TeamsLocalSource


    constructor(remoteDataSource: TeamsRemoteSource,
                localDataSource: TeamsLocalSource
    ) {
        this.remoteDataSource = remoteDataSource
        this.localDataSource = localDataSource
    }

    companion object{
        fun create(teamsApi: TeamsApi, teamsDao: TeamsDao): TeamsRepository {
            val remoteDataSource = TeamsRemoteSource(teamsApi)
            val localDataSource = TeamsLocalSource(teamsDao)
            return TeamsRepositoryImpl(
                remoteDataSource, localDataSource)
        }

    }


    override fun getTeamsFromLocal(): Flow<List<Team>> {

        return localDataSource.getData()
    }

    override fun getTeams(): Flow<Resource<List<Team>>> {
            return remoteDataSource.getData()
    }

    override fun getError(): Flow<String?> {
        return remoteDataSource.onError()

    }

    override suspend fun fetchData() {
        remoteDataSource.fetch(defaultComptId)

        loadResultFromRemote()
    }

    private suspend fun loadResultFromRemote() {
        getTeams().collectLatest {
            when(it.status){
                Status.SUCCESS -> saveDataInCache(it.data)

                Status.ERROR -> Log.e(TAG, "loadResultFromRemote: ${it.message}" )

                else-> Log.d(TAG, "loadResultFromRemote: loading")
            }
        }

    }

    private suspend fun saveDataInCache(teams: List<Team>?) {
        localDataSource.deleteAllTeams() // Reset data
        localDataSource.saveTeams(teams)
    }



}