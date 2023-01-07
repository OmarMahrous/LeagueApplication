package com.digitalcreativity.leagueapplication.ui.competitions.competition_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionDetailsRepository
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionDetailsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionsRepository
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetitionDetailsViewModel(
    comptId:Int,
    competitionDetailsApi: CompetitionDetailsApi,
    leagueDatabase: LeagueDatabase
) : ViewModel() {

    private val competitionDetailsRepository: CompetitionDetailsRepository

    init {
        competitionDetailsRepository = CompetitionDetailsRepositoryImpl
            .create(comptId,competitionDetailsApi, leagueDatabase)
    }

    fun getCompetitionDetails(){
        competitionDetailsRepository.getCompetitionDetails()
    }

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            competitionDetailsRepository.fetchData()
        }
    }

}