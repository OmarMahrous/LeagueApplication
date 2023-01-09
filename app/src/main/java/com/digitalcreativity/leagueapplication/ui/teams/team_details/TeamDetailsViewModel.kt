package com.digitalcreativity.leagueapplication.ui.teams.team_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.repository.teams.TeamDetailsRepository
import com.digitalcreativity.leagueapplication.data.repository.teams.TeamDetailsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.repository.teams.TeamsRepository
import com.digitalcreativity.leagueapplication.data.repository.teams.TeamsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.source.local.teams.TeamsDao
import com.digitalcreativity.leagueapplication.data.source.remote.teams.TeamsApi
import com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details.TeamDetailsApi
import com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details.TeamDetailsResponse
import com.digitalcreativity.leagueapplication.data.util.Resource
import com.digitalcreativity.leagueapplication.ui.teams.TeamsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TeamDetailsViewModel(
    teamId:Int,
    teamDetailsApi: TeamDetailsApi,
    teamsDao: TeamsDao
) : ViewModel() {

    private val teamDetailsRepository:TeamDetailsRepository

    init {
        teamDetailsRepository = TeamDetailsRepositoryImpl
            .create(teamId, teamDetailsApi, teamsDao)
    }

    fun getTeamDetails(): Flow<Resource<TeamDetailsResponse>> {
        return teamDetailsRepository.getTeamDetails()
    }

    fun getTeamDetailsFromLocal(): Flow<Team> {
        return teamDetailsRepository.getTeamsDetailFromLocal()
    }

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            teamDetailsRepository.fetchData()
        }
    }

    internal class TeamDetailsViewModelFactory(val teamId:Int, val teamDetailsApi: TeamDetailsApi,
                                         val teamsDao: TeamsDao
    )
        : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TeamDetailsViewModel::class.java)){
                TeamDetailsViewModel(teamId, teamDetailsApi, teamsDao)  as T
            }else
                throw IllegalArgumentException("ViewModel not found")
        }
    }

}