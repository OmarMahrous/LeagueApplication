package com.digitalcreativity.leagueapplication.ui.teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.digitalcreativity.leagueapplication.data.repository.teams.TeamsRepository
import com.digitalcreativity.leagueapplication.data.repository.teams.TeamsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsApi
import com.digitalcreativity.leagueapplication.data.source.remote.teams.TeamsApi
import com.digitalcreativity.leagueapplication.ui.competitions.competition_details.CompetitionDetailsViewModel
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamsViewModel(
    networkHelper: NetworkHelper,
    comptId:Int,
    teamsApi: TeamsApi,
    leagueDatabase: LeagueDatabase
) : ViewModel() {

    private val teamsRepository: TeamsRepository

    init {
        teamsRepository = TeamsRepositoryImpl
            .create(networkHelper,comptId,teamsApi, leagueDatabase)
    }

    fun getTeams(){
        teamsRepository.getTeams()
    }

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            teamsRepository.fetchData()
        }
    }

    inner class TeamsViewModelFactory(val networkHelper: NetworkHelper,
                                                   val comptId:Int,
                                                   val teamsApi: TeamsApi,
                                                   val leagueDatabase: LeagueDatabase)
        : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TeamsViewModel::class.java)){
                TeamsViewModel(networkHelper, comptId, teamsApi, leagueDatabase)  as T
            }else
                throw IllegalArgumentException("ViewModel not found")
        }
    }

}