package com.digitalcreativity.leagueapplication.data.source.local.competitions

import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.model.CurrentSeason
import com.digitalcreativity.leagueapplication.data.util.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class CompetitionsLocalSource(val competitionsDao: CompetitionsDao) : DataSource<List<Competition>>{

    private val mError: MutableStateFlow<String?> = MutableStateFlow("")




    suspend fun saveCompetitions(competitionList: List<Competition?>?) {
        try {
            competitionsDao.insertCompetitions(competitionList)
        } catch (e: Exception) {
            e.printStackTrace()
            mError.value = (e.message)
        }
    }

    suspend fun saveSeasons(seasonList: List<CurrentSeason?>?) {
        try {
            competitionsDao.insertSeasons(seasonList)
        } catch (e: Exception) {
            e.printStackTrace()
            mError.value = (e.message)
        }
    }

    suspend fun deleteAllSeasons(){
        competitionsDao.deleteAllSeasons()
    }

    suspend fun deleteAllCompetitions(){
        competitionsDao.deleteAllCompetitions()
    }

    fun getCompetitionById(comptId:Int): Flow<Competition> {
        return competitionsDao.getCompetitionById(comptId)
    }

    fun getSeasons(): Flow<List<CurrentSeason>> {
        return competitionsDao.getSeasons()
    }

    override fun getData(): Flow<List<Competition>> {

        return competitionsDao.getCompetitionsFlow()

    }

    override fun onError(): Flow<String?> {
        return mError
    }


}