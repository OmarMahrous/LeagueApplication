package com.digitalcreativity.leagueapplication.data.source.remote.competitions

import com.digitalcreativity.leagueapplication.LeagueApp
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.util.DataSource
import com.digitalcreativity.leagueapplication.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class CompetitionsRemoteSource(val competitionsApi: CompetitionsApi) : DataSource<Resource<List<Competition>>>{

    private val TAG = "CompetitionsRemoteSourc"

    private val mDataApi:MutableStateFlow<Resource<List<Competition>>> = MutableStateFlow(Resource.loading())

    override fun getData(): Flow<Resource<List<Competition>>> {
        return mDataApi

    }

    override fun onError(): Flow<String?> {
        return flowOf(mDataApi.value.message)
    }

    suspend fun fetch(){
        try {
            val response = competitionsApi.getCompetitions()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    val competitionsResponse = response.body()
                    val dataCount = competitionsResponse?.count

                    if (dataCount !=0){
                        competitionsResponse?.competitions?.let {
                            mDataApi.value = Resource.success(it)

                        }
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