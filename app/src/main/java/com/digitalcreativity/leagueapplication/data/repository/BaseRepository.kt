package com.digitalcreativity.leagueapplication.data.repository

import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    fun getError(): Flow<String>

    fun fetchData()
}