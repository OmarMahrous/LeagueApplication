package com.digitalcreativity.leagueapplication.data.repository.competition

import android.util.Log
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsDao
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsLocalSource
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsRemoteSource
import com.digitalcreativity.leagueapplication.data.util.Resource
import com.digitalcreativity.leagueapplication.data.util.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class CompetitionsRepositoryImpl : CompetitionsRepository{

    private val TAG = "CompetitionsRepositoryI"


    private val remoteDataSource: CompetitionsRemoteSource
    private val localDataSource: CompetitionsLocalSource

    private val coroutineScope:CoroutineScope

    constructor(
        remoteDataSource: CompetitionsRemoteSource,
        localDataSource: CompetitionsLocalSource,
                coroutineScope:CoroutineScope
    )
    {
        this.remoteDataSource = remoteDataSource
        this.localDataSource = localDataSource

        this.coroutineScope = coroutineScope
    }

    companion object{
        fun create(competitionsApi: CompetitionsApi, competitionsDao: CompetitionsDao, coroutineScope: CoroutineScope): CompetitionsRepository {
            val remoteDataSource = CompetitionsRemoteSource(competitionsApi)
            val localDataSource = CompetitionsLocalSource(competitionsDao)
            return CompetitionsRepositoryImpl( remoteDataSource, localDataSource, coroutineScope)
        }

    }


override fun getCompetitionsFromLocal(): Flow<List<Competition>> {


    return try{
        val localData = localDataSource.getData()


        localData

    }catch (e:Exception){
        Log.e(TAG, "getCompetitionsFromLocal: error = ${e.message}" )

        flowOf()
    }

}

    override fun getCompetitions(): Flow<Resource<List<Competition?>>> {
              return remoteDataSource.getData()
    }

    override fun getError(): Flow<String?> {
            return remoteDataSource.onError()

    }

    override suspend fun fetchData() {
        remoteDataSource.fetch()

        loadResultFromRemote()
    }

    private suspend fun loadResultFromRemote() {
        val competitionsFromRemote = getCompetitions()

        competitionsFromRemote.collectLatest {
                when(it.status){
                    Status.SUCCESS -> saveCompetitionsInCache(it.data)

                    Status.ERROR -> Log.e(TAG, "loadResultFromRemote: ${it.message}" )

                    else-> Log.d(TAG, "loadResultFromRemote: loading")
                }
            }

    }

    private suspend fun saveCompetitionsInCache(competitions: List<Competition?>?) {

        localDataSource.deleteAllCompetitions() // Reset data
        localDataSource.saveCompetitions(competitions)
    }



}