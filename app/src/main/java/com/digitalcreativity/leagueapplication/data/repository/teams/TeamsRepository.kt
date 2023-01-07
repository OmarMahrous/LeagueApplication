package com.digitalcreativity.leagueapplication.data.repository.teams

import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.repository.BaseRepository
import com.digitalcreativity.leagueapplication.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface TeamsRepository : BaseRepository {

    fun getTeams(): Flow<Resource<List<Team>>>

}