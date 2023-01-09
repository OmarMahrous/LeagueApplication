package com.digitalcreativity.leagueapplication.data.repository.teams

import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.repository.BaseRepository
import com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details.TeamDetailsResponse
import com.digitalcreativity.leagueapplication.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface TeamDetailsRepository : BaseRepository{

    fun getTeamDetails(): Flow<Resource<TeamDetailsResponse>>

    fun getTeamsDetailFromLocal(): Flow<Team>

}