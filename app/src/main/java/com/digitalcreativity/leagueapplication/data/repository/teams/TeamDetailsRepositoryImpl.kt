package com.digitalcreativity.leagueapplication.data.repository.teams

import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.source.local.teams.TeamsDao
import com.digitalcreativity.leagueapplication.data.source.local.teams.TeamsLocalSource
import com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details.TeamDetailsApi
import com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details.TeamDetailsRemoteSource
import com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details.TeamDetailsResponse
import com.digitalcreativity.leagueapplication.data.util.Resource
import kotlinx.coroutines.flow.Flow


class TeamDetailsRepositoryImpl : TeamDetailsRepository {

    private val TAG = "TeamDetailsRepositoryIm"

    private val teamId:Int


    private val remoteDataSource: TeamDetailsRemoteSource
    private val localDataSource: TeamsLocalSource


    constructor(teamId:Int,remoteDataSource: TeamDetailsRemoteSource,
                localDataSource: TeamsLocalSource
    ) {
        this.teamId = teamId
        this.remoteDataSource = remoteDataSource
        this.localDataSource = localDataSource
    }

    companion object{
        fun create(teamId:Int, teamDetailsApi: TeamDetailsApi, teamsDao: TeamsDao): TeamDetailsRepository {
            val remoteDataSource = TeamDetailsRemoteSource(teamDetailsApi)
            val localDataSource = TeamsLocalSource(teamsDao)
            return TeamDetailsRepositoryImpl(teamId,
                remoteDataSource, localDataSource)
        }

    }


    override fun getTeamsDetailFromLocal(): Flow<Team>{

        return localDataSource.getTeamById(teamId)
    }

    override fun getTeamDetails(): Flow<Resource<TeamDetailsResponse>> {
        return remoteDataSource.getData()

    }

    override fun getError(): Flow<String?> {
        return remoteDataSource.onError()

    }

    override suspend fun fetchData() {
        remoteDataSource.fetch(teamId)

    }





}