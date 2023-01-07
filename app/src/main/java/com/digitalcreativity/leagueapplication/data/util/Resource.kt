package com.digitalcreativity.leagueapplication.data.util

data class Resource<out T>(val status: Status, val data:T?, val message:String?){


    companion object{

        fun <T> loading():Resource<T>{
            return Resource(Status.LOADING, null, null)
        }

        fun <T> success(data:T?):Resource<T>{
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String):Resource<T>{
            return Resource(Status.ERROR, null, message)
        }

    }

}
