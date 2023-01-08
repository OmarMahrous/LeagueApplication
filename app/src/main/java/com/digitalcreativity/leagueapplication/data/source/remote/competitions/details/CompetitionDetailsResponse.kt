package com.digitalcreativity.leagueapplication.data.source.remote.competitions.details

import androidx.room.Embedded
import com.digitalcreativity.leagueapplication.data.model.Area
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.model.CurrentSeason
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CompetitionDetailsResponse (

    val id:Int?,
    val area: Area?,
    val name:String?,
    val code:String?,
    val emblemUrl:String?,
    val plan:String?,
    val currentSeason:CurrentSeason?,
    @SerializedName("seasons")
    @Expose
    val seasons: List<CurrentSeason>? = null,
    val numberOfAvailableSeasons:Int?,
    val lastUpdated:String?
)