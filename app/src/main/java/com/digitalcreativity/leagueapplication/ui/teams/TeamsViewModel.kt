package com.digitalcreativity.leagueapplication.ui.teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreativity.leagueapplication.data.repository.teams.TeamsRepository
import com.digitalcreativity.leagueapplication.data.repository.teams.TeamsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.remote.teams.TeamsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamsViewModel(
    comptId:Int,
    teamsApi: TeamsApi,
    leagueDatabase: LeagueDatabase
) : ViewModel() {

    private val teamsRepository: TeamsRepository

    init {
        teamsRepository = TeamsRepositoryImpl
            .create(comptId,teamsApi, leagueDatabase)
    }

    fun getTeams(){
        teamsRepository.getTeams()
    }

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            teamsRepository.fetchData()
        }
    }

}