package com.digitalcreativity.leagueapplication.data.repository.competition

import android.util.Log
import com.digitalcreativity.leagueapplication.LeagueApp
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.model.CurrentSeason
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsDao
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsLocalSource
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsRemoteSource
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsRemoteSource
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsResponse
import com.digitalcreativity.leagueapplication.data.util.Resource
import com.digitalcreativity.leagueapplication.data.util.Status
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf

class CompetitionDetailsRepositoryImpl : CompetitionDetailsRepository{

    private val TAG = "CompetitionDetailsRepos"

    private val defaultCompetitionId = 2000

    private val comptId:Int

    private val remoteDataSource: CompetitionDetailsRemoteSource
    private val localDataSource: CompetitionsLocalSource


    constructor(comptId:Int,remoteDataSource: CompetitionDetailsRemoteSource,
                localDataSource: CompetitionsLocalSource
    )
    {
        this.comptId = comptId
        this.remoteDataSource = remoteDataSource
        this.localDataSource = localDataSource
    }

    companion object{
        fun create(comptId: Int,competitionDetailsApi: CompetitionDetailsApi, competitionsDao: CompetitionsDao): CompetitionDetailsRepository {
            val remoteDataSource = CompetitionDetailsRemoteSource(competitionDetailsApi)
            val localDataSource = CompetitionsLocalSource(competitionsDao)
            return CompetitionDetailsRepositoryImpl(comptId,
                remoteDataSource, localDataSource)
        }

    }


    override fun getCompetitionDetailsFromLocal(): Flow<Competition> {


        return try{
            val localData = localDataSource.getCompetitionById(defaultCompetitionId)

            localData

        }catch (e:Exception){
            Log.e(TAG, "getCompetitionDetailsFromLocal: error = ${e.message}" )

            flowOf()
        }

    }

    override fun getSeasonsFromLocal(): Flow<List<CurrentSeason>> {
        return try{
            val localData = localDataSource.getSeasons()

            localData

        }catch (e:Exception){
            Log.e(TAG, "getSeasonsFromLocal: error = ${e.message}" )

            flowOf()
        }
    }

    override fun getCompetitionDetails(): Flow<Resource<CompetitionDetailsResponse>> {
          return  remoteDataSource.getData()
    }

    override fun getError(): Flow<String?> {
        return remoteDataSource.onError()

    }

    override suspend fun fetchData() {
        remoteDataSource.fetch(defaultCompetitionId)

        loadResultFromRemote()
    }

    private suspend fun loadResultFromRemote() {
        val competitionDetailsFromRemote = getCompetitionDetails()

        competitionDetailsFromRemote.collectLatest {
            when(it.status){
                Status.SUCCESS -> saveSeasonsInCache(it.data?.seasons)

                Status.ERROR -> Log.e(TAG, "loadResultFromRemote: ${it.message}" )

                else-> Log.d(TAG, "loadResultFromRemote: loading")
            }
        }

    }

    private suspend fun saveSeasonsInCache(seasonList: List<CurrentSeason?>?) {
        localDataSource.deleteAllSeasons() // Reset data
        localDataSource.saveSeasons(seasonList)
    }



}