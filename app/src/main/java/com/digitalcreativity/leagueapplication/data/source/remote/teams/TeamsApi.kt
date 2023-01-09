package com.digitalcreativity.leagueapplication.data.source.remote.teams

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamsApi {

    @GET("competitions/{id}/teams")
    suspend fun getTeams(@Path("id")id: Int) : Response<TeamsResponse>

}