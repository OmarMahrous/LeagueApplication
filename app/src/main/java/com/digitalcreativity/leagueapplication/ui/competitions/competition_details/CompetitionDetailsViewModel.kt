package com.digitalcreativity.leagueapplication.ui.competitions.competition_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionDetailsRepository
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionDetailsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionsRepository
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsApi
import com.digitalcreativity.leagueapplication.ui.competitions.CompetitionsViewModel
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetitionDetailsViewModel(
    networkHelper: NetworkHelper,
    comptId:Int,
    competitionDetailsApi: CompetitionDetailsApi,
    leagueDatabase: LeagueDatabase
) : ViewModel() {

    private val competitionDetailsRepository: CompetitionDetailsRepository

    init {
        competitionDetailsRepository = CompetitionDetailsRepositoryImpl
            .create(networkHelper,comptId,
                competitionDetailsApi, leagueDatabase)
    }

    fun getCompetitionDetails(){

        competitionDetailsRepository.getCompetitionDetails()
    }

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            competitionDetailsRepository.fetchData()
        }
    }

    inner class CompetitionDetailsViewModelFactory(val networkHelper: NetworkHelper,
                                                   val comptId:Int,
                                                   val competitionDetailsApi: CompetitionDetailsApi,
                                                   val leagueDatabase: LeagueDatabase)
        : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(CompetitionDetailsViewModel::class.java)){
                CompetitionDetailsViewModel(networkHelper, comptId, competitionDetailsApi, leagueDatabase)  as T
            }else
                throw IllegalArgumentException("ViewModel not found")
        }
    }

}