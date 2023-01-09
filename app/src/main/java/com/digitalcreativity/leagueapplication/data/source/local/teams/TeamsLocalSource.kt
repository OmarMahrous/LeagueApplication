package com.digitalcreativity.leagueapplication.data.source.local.teams

import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.util.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TeamsLocalSource(val teamsDao: TeamsDao) : DataSource<List<Team>> {

    private val mError: MutableStateFlow<String?> = MutableStateFlow("")



    suspend fun saveTeams(teamList: List<Team>?) {
        try {
            teamsDao.insertTeams(teamList)
        } catch (e: Exception) {
            e.printStackTrace()
            mError.value = (e.message)
        }
    }

    suspend fun deleteAllTeams(){
        teamsDao.deleteAllTeams()
    }

    override fun getData(): Flow<List<Team>> {

        return teamsDao.getTeamsFlow()

    }

    fun getTeamById(id:Int): Flow<Team>{
        return teamsDao.getTeamById(id)
    }

    override fun onError(): Flow<String?> {
        return mError
    }


}