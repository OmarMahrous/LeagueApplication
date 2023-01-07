package com.digitalcreativity.leagueapplication.data.source.remote.teams

import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.util.DataSource
import com.digitalcreativity.leagueapplication.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class TeamsRemoteSource(val teamsApi: TeamsApi) :
    DataSource<Resource<List<Team>>> {

    private val TAG = "TeamsRemoteSource"

    private val mDataApi: MutableStateFlow<Resource<List<Team>>> = MutableStateFlow(Resource.loading())

    override fun getData(): Flow<Resource<List<Team>>> {
        return mDataApi

    }

    override fun onError(): Flow<String?> {
        return flowOf(mDataApi.value.message)
    }

    suspend fun fetch(comptId:Int){
        val response = teamsApi.getTeams(comptId)
        withContext(Dispatchers.Main){
            if (response.isSuccessful){
                val teamsResponse = response.body()
                val dataCount = teamsResponse?.count

                if (dataCount !=0){
                    teamsResponse?.teams?.let { mDataApi.value = Resource.success(it) }
                }else
                    mDataApi.value = Resource.error(response.message())
            }else
                mDataApi.value = Resource.error(response.message())


        }
    }




}