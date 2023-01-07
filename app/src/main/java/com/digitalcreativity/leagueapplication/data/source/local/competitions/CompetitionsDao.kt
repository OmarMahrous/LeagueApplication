package com.digitalcreativity.leagueapplication.data.source.local.competitions

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.digitalcreativity.leagueapplication.data.model.Competition
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetitionsDao {

    @Insert
    suspend fun addCompetitionToDB(competition: Competition)


    @Query("SELECT * FROM competition_table")
    fun getCompetitions(): Flow<List<Competition>>

    @Query("SELECT * FROM competition_table WHERE (id == :comptId)")
    fun getCompetitionById(comptId: Int): Flow<Competition>

}