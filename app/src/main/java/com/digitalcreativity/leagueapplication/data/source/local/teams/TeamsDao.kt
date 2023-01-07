package com.digitalcreativity.leagueapplication.data.source.local.teams

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.digitalcreativity.leagueapplication.data.model.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(teamList: List<Team>?)


    @Query("SELECT * FROM team_table")
    fun getTeamsFlow(): Flow<List<Team>>

    @Query("SELECT * FROM team_table")
    fun getTeams(): List<Team>

}