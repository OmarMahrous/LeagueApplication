package com.digitalcreativity.leagueapplication.data.repository.teams

import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

interface TeamsRepository : BaseRepository {

    fun getTeams(): Flow<List<Team>>

}