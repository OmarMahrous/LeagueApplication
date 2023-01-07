package com.digitalcreativity.leagueapplication.data.repository.competition

import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

interface CompetitionsRepository : BaseRepository {

    fun getCompetitions(): Flow<List<Competition>>

}