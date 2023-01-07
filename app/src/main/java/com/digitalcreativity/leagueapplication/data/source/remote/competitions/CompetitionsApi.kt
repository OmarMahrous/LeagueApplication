package com.digitalcreativity.leagueapplication.data.source.remote.competitions

import retrofit2.Response
import retrofit2.http.GET

interface CompetitionsApi {

    @GET("competitions")
    suspend fun getCompetitions() : Response<CompetitionsResponse>

}