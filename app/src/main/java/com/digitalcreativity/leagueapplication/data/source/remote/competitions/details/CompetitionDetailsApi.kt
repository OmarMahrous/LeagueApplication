package com.digitalcreativity.leagueapplication.data.source.remote.competitions.details

import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CompetitionDetailsApi {

    @GET("competitions/{id}")
    suspend fun getCompetitionDetails(@Path("id")id: Int) : Response<CompetitionDetailsResponse>


}