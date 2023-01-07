package com.digitalcreativity.leagueapplication.data.source.local.competitions

import androidx.room.*
import com.digitalcreativity.leagueapplication.data.model.Competition
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetitionsDao {




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompetitions(competitionList: List<Competition?>?)


    @Query("SELECT * FROM competition_table")
    fun getCompetitionsFlow(): Flow<List<Competition>>

    @Query("SELECT * FROM competition_table")
    fun getCompetitions(): List<Competition>

    @Query("SELECT * FROM competition_table WHERE (id == :comptId)")
    fun getCompetitionById(comptId: Int): Competition

}