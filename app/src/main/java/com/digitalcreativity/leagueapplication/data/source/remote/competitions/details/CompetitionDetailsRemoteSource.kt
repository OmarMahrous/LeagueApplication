package com.digitalcreativity.leagueapplication.data.source.remote.competitions.details

import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import com.digitalcreativity.leagueapplication.data.util.DataSource
import com.digitalcreativity.leagueapplication.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class CompetitionDetailsRemoteSource(val competitionDetailsApi: CompetitionDetailsApi) :
        DataSource<Resource<CompetitionDetailsResponse>> {

    private val TAG = "CompetitionDetailsRemot"

        private val mDataApi: MutableStateFlow<Resource<CompetitionDetailsResponse>> = MutableStateFlow(
            Resource.loading())

        override fun getData(): Flow<Resource<CompetitionDetailsResponse>> {
            return mDataApi

        }

        override fun onError(): Flow<String?> {
            return flowOf(mDataApi.value.message)
        }

        suspend fun fetch(comptId:Int){
            try {
                val response = competitionDetailsApi.getCompetitionDetails(comptId)
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        val competitionResponse = response.body()

                        if (response.code() == 200){
                            competitionResponse?.let { mDataApi.value = Resource.success(it) }
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