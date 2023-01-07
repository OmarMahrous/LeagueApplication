package com.digitalcreativity.leagueapplication.data.source.remote

import com.digitalcreativity.leagueapplication.data.source.remote.api_service.ApiUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiGenerator {

    private val BASE_URL = "http://api.football-data.org/v2/competitions"

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()


    fun initRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return retrofitBuilder.baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .build()

    }

    fun addOkHttpClient(): OkHttpClient {

         val builder  = OkHttpClient.Builder()

         builder.addInterceptor(authInterceptor())

         builder.networkInterceptors().add(loggingInterceptor())

         return builder.build()
    }

    private fun authInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()

            requestBuilder.addHeader("X-Auth-Token", ApiUtil.API_KEY);
            chain.proceed(requestBuilder.build())
        }
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return loggingInterceptor
    }

    fun <T> createApiService(retrofit: Retrofit, apiClass: Class<T>):T{
        return retrofit.create(apiClass)
    }

}