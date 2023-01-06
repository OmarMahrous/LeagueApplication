package com.digitalcreativity.leagueapplication.data.source.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiGenerator {

    private val BASE_URL = "http://api.football-data.org/v2/competitions"

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()


    fun initRetrofit(): Retrofit {

        return retrofitBuilder.baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

    }

    fun <T> createApiService(retrofit: Retrofit, apiClass: Class<T>):T{
        return retrofit.create(apiClass)
    }

}