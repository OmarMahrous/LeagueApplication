package com.digitalcreativity.leagueapplication.ui.competitions.competition_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.model.CurrentSeason
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionDetailsRepository
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionDetailsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionsRepository
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsDao
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsResponse
import com.digitalcreativity.leagueapplication.data.util.Resource
import com.digitalcreativity.leagueapplication.ui.competitions.CompetitionsViewModel
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CompetitionDetailsViewModel(
    comptId:Int,
    competitionDetailsApi: CompetitionDetailsApi,
    competitionsDao: CompetitionsDao
) : ViewModel() {

    private val competitionDetailsRepository: CompetitionDetailsRepository

    init {
        competitionDetailsRepository = CompetitionDetailsRepositoryImpl
            .create(comptId,
                competitionDetailsApi, competitionsDao)
    }

    fun getCompetitionDetails(): Flow<Resource<CompetitionDetailsResponse>> {

        return competitionDetailsRepository.getCompetitionDetails()
    }

    fun getCompetitionDetailsFromLocal(): Flow<Competition> {

        return competitionDetailsRepository.getCompetitionDetailsFromLocal()

    }

    fun getSeasonsFromLocal(): Flow<List<CurrentSeason>> {

        return competitionDetailsRepository.getSeasonsFromLocal()

    }

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            competitionDetailsRepository.fetchData()
        }
    }

    internal class CompetitionDetailsViewModelFactory(
                                                   val comptId:Int,
                                                   val competitionDetailsApi: CompetitionDetailsApi,
                                                   val competitionsDao: CompetitionsDao)
        : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(CompetitionDetailsViewModel::class.java)){
                CompetitionDetailsViewModel(comptId, competitionDetailsApi, competitionsDao)  as T
            }else
                throw IllegalArgumentException("ViewModel not found")
        }
    }

}