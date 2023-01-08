package com.digitalcreativity.leagueapplication.ui.util

import androidx.navigation.NavController
import com.digitalcreativity.leagueapplication.ui.competitions.CompetitionsFragmentDirections
import com.digitalcreativity.leagueapplication.ui.competitions.competition_details.CompetitionDetailsFragmentDirections


class ScreensNavigator {

    companion object{

        fun navigateToCompetitionDetails(navController: NavController, comptId:Int){

            val action = CompetitionsFragmentDirections
                .actionCompetitionsFragmentToCompetitionDetailsFragment(comptId)

            navController.navigate(action)
        }

        fun navigateToTeamDetails(navController: NavController, teamId:Int){

            val action = CompetitionDetailsFragmentDirections
                .actionCompetitionDetailsFragmentToTeamDetailsFragment(teamId)
            navController.navigate(action)
        }

    }

}