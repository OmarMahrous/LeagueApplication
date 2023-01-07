package com.digitalcreativity.leagueapplication.ui.competitions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionsRepository
import com.digitalcreativity.leagueapplication.data.repository.competition.CompetitionsRepositoryImpl
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetitionsViewModel(
    val competitionsApi: CompetitionsApi, val leagueDatabase: LeagueDatabase
) : ViewModel() {

    private val competitionsRepository:CompetitionsRepository

    init {
        competitionsRepository = CompetitionsRepositoryImpl
            .create(competitionsApi, leagueDatabase)
    }

    fun getCompetitions(){
        competitionsRepository.getCompetitions()
    }

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            competitionsRepository.fetchData()
        }
    }

}