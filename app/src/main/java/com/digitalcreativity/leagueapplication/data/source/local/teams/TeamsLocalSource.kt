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

    fun getTeams(): List<Team> {
        return teamsDao.getTeams()
    }

    override fun getData(): Flow<List<Team>> {

        return teamsDao.getTeamsFlow()

    }

    override fun onError(): Flow<String?> {
        return mError
    }


}