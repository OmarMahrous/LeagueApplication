package com.digitalcreativity.leagueapplication.utils

import com.digitalcreativity.leagueapplication.data.model.Competition
import org.mockito.ArgumentMatcher

class CompetitionEntityMatcher : ArgumentMatcher<List<Competition>> {
    private lateinit var competitions: List<Competition>

    constructor(competitions: List<Competition>) {
        this.competitions = competitions;
    }



    override fun matches(argument: List<Competition>): Boolean {
        if (competitions.size == 0)
            return true
        val size = this.competitions.size
        for (i in 0..size-1)
        if (!(competitions[i].id?.let {
                this.competitions[i].id
                    ?.compareTo(it)
            } == 0))
            return false;
        return true;
    }

}