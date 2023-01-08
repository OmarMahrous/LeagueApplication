package com.digitalcreativity.leagueapplication.data.source.remote.competitions.details

import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.model.CurrentSeason
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CompetitionDetailsResponse {


    val competitionInfo: Competition? = null


    @SerializedName("seasons")
    @Expose
    val seasons: List<CurrentSeason>? = null
}