package com.digitalcreativity.leagueapplication.data.repository.teams

import android.util.Log
import com.digitalcreativity.leagueapplication.LeagueApp
import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
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

    private val comptId:Int

    private val networkHelper:NetworkHelper

    private val remoteDataSource: TeamsRemoteSource
    private val localDataSource: TeamsLocalSource


    constructor(networkHelper: NetworkHelper,comptId:Int, remoteDataSource: TeamsRemoteSource,
                localDataSource: TeamsLocalSource
    ) {
        this.networkHelper = networkHelper
        this.comptId = comptId
        this.remoteDataSource = remoteDataSource
        this.localDataSource = localDataSource
    }

    companion object{
        fun create(networkHelper: NetworkHelper,comptId: Int, teamsApi: TeamsApi, leagueDatabase: LeagueDatabase): TeamsRepository {
            val remoteDataSource = TeamsRemoteSource(teamsApi)
            val localDataSource = TeamsLocalSource(leagueDatabase)
            return TeamsRepositoryImpl(networkHelper,comptId,
                remoteDataSource, localDataSource)
        }

    }


    private fun getTeamsFromLocal(): Flow<Resource<List<Team>>> {

        val mutableStateFlow: MutableStateFlow<Resource<List<Team>>> =
            MutableStateFlow(Resource.success(localDataSource.getTeams()))


        return mutableStateFlow
    }

    override fun getTeams(): Flow<Resource<List<Team>>> {
        return if (networkHelper.isNetworkConnected())
            remoteDataSource.getData()
        else
            getTeamsFromLocal()
    }

    override fun getError(): Flow<String?> {
        return remoteDataSource.onError()

    }

    override suspend fun fetchData() {
        remoteDataSource.fetch(comptId)

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
        localDataSource.saveTeams(teams)
    }



}