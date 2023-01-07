package com.digitalcreativity.leagueapplication.data.repository.competition

import android.util.Log
import com.digitalcreativity.leagueapplication.LeagueApp
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsLocalSource
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsRemoteSource
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsRemoteSource
import com.digitalcreativity.leagueapplication.data.util.Resource
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class CompetitionDetailsRepositoryImpl : CompetitionDetailsRepository{

    private val TAG = "CompetitionDetailsRepos"

    private val comptId:Int

    private val networkHelper: NetworkHelper

    private val remoteDataSource: CompetitionDetailsRemoteSource
    private val localDataSource: CompetitionsLocalSource


    constructor(networkHelper: NetworkHelper,comptId:Int,remoteDataSource: CompetitionDetailsRemoteSource,
                localDataSource: CompetitionsLocalSource
    )
    {
        this.networkHelper = networkHelper
        this.comptId = comptId
        this.remoteDataSource = remoteDataSource
        this.localDataSource = localDataSource
    }

    companion object{
        fun create(networkHelper: NetworkHelper,comptId: Int,competitionDetailsApi: CompetitionDetailsApi, leagueDatabase: LeagueDatabase): CompetitionDetailsRepository {
            val remoteDataSource = CompetitionDetailsRemoteSource(competitionDetailsApi)
            val localDataSource = CompetitionsLocalSource(leagueDatabase)
            return CompetitionDetailsRepositoryImpl(networkHelper,comptId,
                remoteDataSource, localDataSource)
        }

    }


    private fun getCompetitionDetailsFromLocal(): Flow<Resource<Competition>> {

        val mutableStateFlow: MutableStateFlow<Resource<Competition>> =
            MutableStateFlow(Resource.success(localDataSource.getCompetitionById(comptId)))


        return mutableStateFlow
    }

    override fun getCompetitionDetails(): Flow<Resource<Competition>> {
        return if (networkHelper.isNetworkConnected())
            remoteDataSource.getData()
        else
            getCompetitionDetailsFromLocal()
    }

    override fun getError(): Flow<String?> {
        return remoteDataSource.onError()

    }

    override suspend fun fetchData() {
        remoteDataSource.fetch(comptId)

    }



}