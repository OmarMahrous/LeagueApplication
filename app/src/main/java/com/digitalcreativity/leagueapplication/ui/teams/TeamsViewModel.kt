package com.digitalcreativity.leagueapplication.ui.teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.repository.teams.TeamsRepository
import com.digitalcreativity.leagueapplication.data.repository.teams.TeamsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.local.teams.TeamsDao
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsApi
import com.digitalcreativity.leagueapplication.data.source.remote.teams.TeamsApi
import com.digitalcreativity.leagueapplication.data.util.Resource
import com.digitalcreativity.leagueapplication.ui.competitions.competition_details.CompetitionDetailsViewModel
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TeamsViewModel(
    teamsApi: TeamsApi,
    teamsDao: TeamsDao
) : ViewModel() {

    private val teamsRepository: TeamsRepository

    init {
        teamsRepository = TeamsRepositoryImpl
            .create(teamsApi, teamsDao)
    }

    fun getTeams(): Flow<Resource<List<Team>>> {
        return teamsRepository.getTeams()
    }

    fun getTeamsFromLocal(): Flow<List<Team>> {
        return teamsRepository.getTeamsFromLocal()
    }

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            teamsRepository.fetchData()
        }
    }

    internal class TeamsViewModelFactory(val teamsApi: TeamsApi,
                                         val teamsDao: TeamsDao)
        : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TeamsViewModel::class.java)){
                TeamsViewModel(teamsApi, teamsDao)  as T
            }else
                throw IllegalArgumentException("ViewModel not found")
        }
    }

}