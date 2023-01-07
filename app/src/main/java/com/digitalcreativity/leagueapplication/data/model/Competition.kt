package com.digitalcreativity.leagueapplication.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "competition_table")
data class Competition(val id:Int?,
                       @Embedded val area:Area?,
                       val name:String?,
                       val code:String?,
                       val plan:String?,
                       @Embedded val currentSeason:CurrentSeason?,
                       val numberOfAvailableSeasons:Int?,
                       val lastUpdated:String?,
                       @PrimaryKey(autoGenerate = true) val comptId: Int
)

data class Area(
                @Ignore
                @SerializedName("id")
                var a_id:Int?=null,
                @Ignore
                @SerializedName("name")
                var a_name:String?=null,
                var countryCode:String?=null,
                var ensignUrl:String?=null
                )

data class CurrentSeason(@Ignore
                         @SerializedName("id")
                         var s_id:Int?=null,
                         var startDate:String?=null,
                         var endDate:String?=null,
                         var currentMatchday:Int?=null
                         )