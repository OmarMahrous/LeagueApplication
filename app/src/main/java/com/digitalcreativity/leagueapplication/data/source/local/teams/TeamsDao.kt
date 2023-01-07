package com.digitalcreativity.leagueapplication.data.source.local.teams

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.digitalcreativity.leagueapplication.data.model.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamsDao {

    @Insert
    suspend fun addTeamToDB(team: Team)


    @Query("SELECT * FROM team_table")
    fun getTeams(): Flow<List<Team>>

}