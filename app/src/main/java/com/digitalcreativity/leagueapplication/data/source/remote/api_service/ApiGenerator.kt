package com.digitalcreativity.leagueapplication.data.source.remote.api_service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiGenerator {

    private val BASE_URL = "http://api.football-data.org/v2/competitions"

    fun <T> setupBaseApi(apiClass: Class<T>): T {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(apiClass)
    }

}