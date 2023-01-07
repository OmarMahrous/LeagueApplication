package com.digitalcreativity.leagueapplication.data.util

import kotlinx.coroutines.flow.Flow

interface DataSource<T> {

    fun getData(): Flow<T>

    fun getError():Flow<String>
}