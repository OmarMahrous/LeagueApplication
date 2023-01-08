package com.digitalcreativity.leagueapplication.data.repository.competition

import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.repository.BaseRepository
import com.digitalcreativity.leagueapplication.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface CompetitionsRepository : BaseRepository {


    fun getCompetitions(): Flow<Resource<List<Competition?>>>

    fun getCompetitionsFromLocal(): Flow<List<Competition>>

}