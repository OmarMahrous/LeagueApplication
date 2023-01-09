package com.digitalcreativity.leagueapplication.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class Team(val id:Int?,
                @Embedded val area:Area?,
                val name:String?,
                val shortName:String?,
                val tla:String?,
                val crestUrl:String?,
                val address:String?,
                val phone:String?,
                val website:String?,
                val email:String?,
                val founded:Int?,
                val clubColors:String?,
                val venue:String?,
                val lastUpdated:String?,
                @PrimaryKey(autoGenerate = true) val teamId: Int
)

data class Squad(val id:Int?,
                 val name:String?,
                 val position:String?,
                 val nationality:String?
)
