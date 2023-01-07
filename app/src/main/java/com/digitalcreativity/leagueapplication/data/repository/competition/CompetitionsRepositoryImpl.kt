package com.digitalcreativity.leagueapplication.data.repository.competition

import android.content.Context
import android.util.Log
import com.digitalcreativity.leagueapplication.LeagueApp
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsLocalSource
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsRemoteSource
import com.digitalcreativity.leagueapplication.data.util.Resource
import com.digitalcreativity.leagueapplication.data.util.Status
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CompetitionsRepositoryImpl : CompetitionsRepository{

    private val TAG = "CompetitionsRepositoryI"

    private val networkHelper = NetworkHelper(LeagueApp.getAppContext())

    private val remoteDataSource: CompetitionsRemoteSource
    private val localDataSource: CompetitionsLocalSource


    constructor(remoteDataSource: CompetitionsRemoteSource,
        localDataSource: CompetitionsLocalSource
    )
    {

        this.remoteDataSource = remoteDataSource
        this.localDataSource = localDataSource
    }

    companion object{
        fun create(competitionsApi: CompetitionsApi, leagueDatabase: LeagueDatabase): CompetitionsRepository {
            val remoteDataSource = CompetitionsRemoteSource(competitionsApi)
            val localDataSource = CompetitionsLocalSource(leagueDatabase)
            return CompetitionsRepositoryImpl(remoteDataSource, localDataSource)
        }

    }


private fun getCompetitionsFromLocal():Flow<Resource<List<Competition?>>>{

    val mutableStateFlow: MutableStateFlow<Resource<List<Competition?>>> =
        MutableStateFlow(Resource.success(localDataSource.getCompetitions()))

    return mutableStateFlow.asStateFlow()
}

    override fun getCompetitions(): Flow<Resource<List<Competition?>>> {
        return if (networkHelper.isNetworkConnected())
                    remoteDataSource.getData()
               else
                    getCompetitionsFromLocal()
    }

    override fun getError(): Flow<String?> {
            return remoteDataSource.onError()

    }

    override suspend fun fetchData() {
        remoteDataSource.fetch()

        loadResultFromRemote()
    }

    private suspend fun loadResultFromRemote() {
            getCompetitions().collectLatest {
                when(it.status){
                    Status.SUCCESS -> saveDataInCache(it.data)

                    Status.ERROR -> Log.e(TAG, "loadResultFromRemote: ${it.message}" )

                    else-> Log.d(TAG, "loadResultFromRemote: loading")
                }
            }


    }

    private suspend fun saveDataInCache(competitions: List<Competition?>?) {
        localDataSource.saveCompetitions(competitions)
    }

}