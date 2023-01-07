package com.digitalcreativity.leagueapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "competition_table")
data class Competition(val id:Int?,
                       val area:Area?,
                       val name:String?,
                       val code:String?,
                       val plan:String?,
                       val currentSeason:CurrentSeason?,
                       val numberOfAvailableSeasons:Int?,
                       val lastUpdated:String?,
                       @PrimaryKey(autoGenerate = true) val comptId: Int
)

data class Area(val id:Int?,
                val name:String?,
                val countryCode:String?,
                val ensignUrl:String?
                )

data class CurrentSeason(val id:Int?,
                         val startDate:String?,
                         val endDate:String?,
                         val currentMatchday:Int?
                         )