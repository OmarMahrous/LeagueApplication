package com.digitalcreativity.leagueapplication.data.source.local.competitions

import com.digitalcreativity.leagueapplication.data.model.Competition
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

    suspend fun deleteAllCompetitions(){
        competitionsDao.deleteAllCompetitions()
    }

     fun getCompetitions(): List<Competition?>? {
        return competitionsDao.getCompetitions()
    }


    fun getCompetitionById(comptId:Int): Competition {
        return competitionsDao.getCompetitionById(comptId)
    }

    override fun getData(): Flow<List<Competition>> {

        return competitionsDao.getCompetitionsFlow()

    }

    override fun onError(): Flow<String?> {
        return mError
    }


}