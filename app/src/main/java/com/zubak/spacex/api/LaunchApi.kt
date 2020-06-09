package com.zubak.spacex.api

import retrofit2.http.GET
import retrofit2.Call
import com.zubak.spacex.data.Launches

interface LaunchApi {

    @GET("launches")
    fun getAllLaunches() : Call<Launches>

    @GET("launches/past")
    fun getPastLaunches() : Call<Launches>

    @GET("launches/upcoming")
    fun getUpcomingLaunches() : Call<Launches>
}
