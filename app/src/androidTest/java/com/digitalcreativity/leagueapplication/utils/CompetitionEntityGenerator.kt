package com.digitalcreativity.leagueapplication.utils

import com.digitalcreativity.leagueapplication.data.model.Competition
import java.util.*

class CompetitionEntityGenerator {

    companion object{
        private var id = 0

        private val rand: Random = Random()

        fun createRandomEntity(): Competition? {
            return  Competition(
                id++,
                null,
                "TestCompetition",
                "c1",
                "comp_url",
                "comp_plan",
                null,
                rand.nextInt(100),
                "5-1-2023",
                rand.nextInt(100)
            )
        }
    }


}