package com.digitalcreativity.leagueapplication.ui.competitions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionsRepository
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsDao
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import com.digitalcreativity.leagueapplication.data.util.Resource
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CompetitionsViewModel(
    competitionsApi: CompetitionsApi,
    competitionsDao: CompetitionsDao
) : ViewModel() {

    private val competitionsRepository:CompetitionsRepository

    init {
        competitionsRepository = CompetitionsRepositoryImpl
            .create(competitionsApi, competitionsDao, viewModelScope)
    }

    fun getCompetitions(): Flow<Resource<List<Competition?>>> {
        return competitionsRepository.getCompetitions()
    }

    fun getCompetitionsFromLocal(): Flow<List<Competition>> {

        return competitionsRepository.getCompetitionsFromLocal()

    }



    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            competitionsRepository.fetchData()
        }
    }

    internal class CompetitionsViewModelFactory(
                             val competitionsApi: CompetitionsApi,
                             val competitionsDao: CompetitionsDao)
        : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(CompetitionsViewModel::class.java)){
              CompetitionsViewModel( competitionsApi, competitionsDao)  as T
            }else
                throw IllegalArgumentException("ViewModel not found")
        }
    }

}