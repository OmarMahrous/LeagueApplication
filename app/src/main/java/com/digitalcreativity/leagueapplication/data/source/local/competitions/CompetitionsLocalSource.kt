package com.digitalcreativity.leagueapplication.data.source.local.competitions

import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.util.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class CompetitionsLocalSource(val leagueDatabase:LeagueDatabase) : DataSource<List<Competition>>{

    private val mError: MutableStateFlow<String?> = MutableStateFlow("")




    suspend fun saveCompetitions(competitionList: List<Competition?>?) {
        try {
            leagueDatabase.competitionsDao().insertCompetitions(competitionList)
        } catch (e: Exception) {
            e.printStackTrace()
            mError.value = (e.message)
        }
    }

    fun getCompetitions(): List<Competition?>? {
        return leagueDatabase.competitionsDao().getCompetitions()
    }


    fun getCompetitionById(comptId:Int): Flow<Competition> {
        return leagueDatabase.competitionsDao().getCompetitionById(comptId)
    }

    override fun getData(): Flow<List<Competition>> {

        return leagueDatabase.competitionsDao().getCompetitionsFlow()

    }

    override fun onError(): Flow<String?> {
        return mError
    }


}