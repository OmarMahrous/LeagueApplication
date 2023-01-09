package com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details

import com.digitalcreativity.leagueapplication.data.util.DataSource
import com.digitalcreativity.leagueapplication.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class TeamDetailsRemoteSource(val teamDetailsApi: TeamDetailsApi) :
    DataSource<Resource<TeamDetailsResponse>> {

    private val TAG = "TeamDetailsRemoteSource"

    private val mDataApi: MutableStateFlow<Resource<TeamDetailsResponse>> = MutableStateFlow(Resource.loading())

    override fun getData(): Flow<Resource<TeamDetailsResponse>> {
        return mDataApi

    }

    override fun onError(): Flow<String?> {
        return flowOf(mDataApi.value.message)
    }

    suspend fun fetch(teamId:Int){
        try {
            val response = teamDetailsApi.getTeamDetails(teamId)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    val teamResponse = response.body()


                    if (response.code() == 200){
                        teamResponse?.let { mDataApi.value = Resource.success(it) }
                    }else
                        mDataApi.value = Resource.error(response.message())
                }else
                    mDataApi.value = Resource.error(response.message())


            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }




}