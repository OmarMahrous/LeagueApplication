package com.digitalcreativity.leagueapplication.ui.util

import androidx.navigation.NavController
import com.digitalcreativity.leagueapplication.ui.competitions.CompetitionsFragmentDirections
import com.digitalcreativity.leagueapplication.ui.competitions.competition_details.CompetitionDetailsFragmentDirections
import com.digitalcreativity.leagueapplication.ui.teams.TeamsFragmentDirections


class ScreensNavigator {

    companion object{

        fun navigateToCompetitionDetails(navController: NavController, comptId:Int){

            val action = CompetitionsFragmentDirections
                .actionCompetitionsFragmentToCompetitionDetailsFragment(comptId)

            navController.navigate(action)
        }

        fun navigateToTeamsPage(navController: NavController){

            val action = CompetitionDetailsFragmentDirections
                .actionCompetitionDetailsFragmentToTeamsFragment(0)
            navController.navigate(action)
        }

        fun navigateToTeamDetails(navController: NavController, teamId:Int){
            val action = TeamsFragmentDirections
                .actionTeamsFragmentToTeamDetailsFragment(teamId)
            navController.navigate(action)
        }

    }

}