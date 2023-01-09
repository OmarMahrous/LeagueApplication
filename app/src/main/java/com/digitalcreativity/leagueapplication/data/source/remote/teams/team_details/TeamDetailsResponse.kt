package com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details

import com.digitalcreativity.leagueapplication.data.model.Area
import com.digitalcreativity.leagueapplication.data.model.CurrentSeason
import com.digitalcreativity.leagueapplication.data.model.Squad
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TeamDetailsResponse (

    val id:Int?,
    val area: Area?,
    val name:String?,
    val tla:String?,
    val crestUrl:String?,
    val address:String?,
    val phone:String?,
    val email: String?,
    @SerializedName("squad")
    @Expose
    val squadPlayers: List<Squad>? = null,
    val founded:Int?,
    val venue:String?

)