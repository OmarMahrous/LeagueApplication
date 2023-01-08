package com.digitalcreativity.leagueapplication.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.model.CurrentSeason
import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsDao
import com.digitalcreativity.leagueapplication.data.source.local.teams.TeamsDao

@Database(entities = [Competition::class, Team::class, CurrentSeason::class], version = 7)
abstract class LeagueDatabase : RoomDatabase() {

    abstract fun competitionsDao():CompetitionsDao

    abstract fun teamsDao():TeamsDao

}