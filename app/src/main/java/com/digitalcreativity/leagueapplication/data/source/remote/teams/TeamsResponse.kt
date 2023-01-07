package com.digitalcreativity.leagueapplication.data.source.remote.teams

import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.source.remote.BaseResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TeamsResponse : BaseResponse() {

    @SerializedName("teams")
    @Expose
    val teams: List<Team>? = null

}