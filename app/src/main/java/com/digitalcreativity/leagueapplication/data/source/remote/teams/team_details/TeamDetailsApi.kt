package com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamDetailsApi {

    @GET("teams/{id}")
    suspend fun getTeamDetails(@Path("id")id: Int) : Response<TeamDetailsResponse>

}