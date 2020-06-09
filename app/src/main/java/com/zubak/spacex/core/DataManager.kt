package com.zubak.spacex.core

import com.zubak.spacex.api.LaunchApi
import com.zubak.spacex.api.LaunchesType
import com.zubak.spacex.data.Launches
import com.zubak.spacex.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataManager {
    private var apiInterface = RetrofitService().getClient()?.create(LaunchApi::class.java)

    fun getLaunches(launchesType: LaunchesType) {
        val call: Call<Launches>? = when(launchesType) {
            LaunchesType.ALL -> apiInterface?.getAllLaunches()
            LaunchesType.PAST -> apiInterface?.getPastLaunches()
            LaunchesType.UPCOMING -> apiInterface?.getUpcomingLaunches()
        }

        call?.enqueue(object : Callback<Launches> {
            override fun onResponse(
                call: Call<Launches>,
                response: Response<Launches>
            ) {
                val launches: Launches? = response.body()
            }

            override fun onFailure(call: Call<Launches>, t: Throwable?) {
                call.cancel()
            }
        })
    }

}