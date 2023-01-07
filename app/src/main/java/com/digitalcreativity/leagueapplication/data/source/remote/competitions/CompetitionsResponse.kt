package com.digitalcreativity.leagueapplication.data.source.remote.competitions

import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.source.remote.BaseResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CompetitionsResponse : BaseResponse() {

    @SerializedName("competitions")
    @Expose
    val competitions: List<Competition>? = null

}