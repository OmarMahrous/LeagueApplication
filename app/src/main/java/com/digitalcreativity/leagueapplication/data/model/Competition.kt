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
                       val emblemUrl:String?,
                       val plan:String?,
                       @Embedded val currentSeason:CurrentSeason?,
                       val numberOfAvailableSeasons:Int?,
                       val lastUpdated:String?,
                       @PrimaryKey(autoGenerate = true) val comptId: Int
)

data class Area(
                @SerializedName("id")
                var a_id:Int?=null,
                @SerializedName("name")
                var a_name:String?=null,
                var countryCode:String?=null,
                var ensignUrl:String?=null
                )

@Entity(tableName = "season_table")
data class CurrentSeason(@Ignore
                         @SerializedName("id")
                         var s_id:Int?=null,
                         var startDate:String?=null,
                         var endDate:String?=null,
                         var currentMatchday:Int?=null,
                         @Embedded var winner:Winner?=null,
                         @PrimaryKey(autoGenerate = true) var seasonId: Int?=null
)

data class Winner(@Ignore
                  @SerializedName("id")
                  var w_id:Int?=null,
                  @SerializedName("name")
                  var w_name:String?=null,
                  var tla:String?=null,
                  var crestUrl:String?=null
)