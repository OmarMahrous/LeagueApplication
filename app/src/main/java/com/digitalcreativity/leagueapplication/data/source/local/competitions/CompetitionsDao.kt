package com.digitalcreativity.leagueapplication.data.source.local.competitions

import androidx.annotation.VisibleForTesting
import androidx.room.*
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.model.CurrentSeason
import kotlinx.coroutines.flow.Flow


@Dao
interface CompetitionsDao {

    @VisibleForTesting
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCompetitions(competitionList: List<Competition?>?)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompetitions(competitionList: List<Competition?>?)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasonList: List<CurrentSeason?>?)


    @Query("SELECT * FROM season_table")
    fun getSeasons(): Flow<List<CurrentSeason>>


    @Query("SELECT * FROM competition_table")
    fun getCompetitionsFlow(): Flow<List<Competition>>


    @Query("SELECT * FROM competition_table WHERE (id == :comptId)")
    fun getCompetitionById(comptId: Int): Flow<Competition>


    @VisibleForTesting
    @Query("DELETE FROM competition_table")
    suspend fun deleteAllCompetitions()

    @Query("DELETE FROM season_table")
    suspend fun deleteAllSeasons()

}