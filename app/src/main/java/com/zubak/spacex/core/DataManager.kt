package com.zubak.spacex.core


import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.zubak.spacex.R
import com.zubak.spacex.api.LaunchApi
import com.zubak.spacex.api.LaunchesType
import com.zubak.spacex.data.Launches
import com.zubak.spacex.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader


class DataManager {
    private var apiInterface = RetrofitService().getClient().create(LaunchApi::class.java)

    fun getLaunches(launchesType: LaunchesType,
                    launches: MutableLiveData<Launches>
    ) {
        val call: Call<Launches>? = when(launchesType) {
            LaunchesType.ALL -> apiInterface.getAllLaunches()
            LaunchesType.PAST -> apiInterface.getPastLaunches()
            LaunchesType.UPCOMING -> apiInterface.getUpcomingLaunches()
        }

        call?.enqueue(object : Callback<Launches> {
            override fun onResponse(
                call: Call<Launches>,
                response: Response<Launches>
            ) {
                launches.value = response.body()
            }

            override fun onFailure(call: Call<Launches>, t: Throwable?) {
                call.cancel()
            }
        })
    }

    fun getLaunchesFromCache(launchesType: LaunchesType, context: Context): Launches {
        val file: InputStream = context.resources.openRawResource(
            when(launchesType) {
            LaunchesType.ALL -> R.raw.alllaunches
            LaunchesType.PAST -> R.raw.pastlaunches
            LaunchesType.UPCOMING -> R.raw.upcominglaunches
        })
        val reader : Reader = InputStreamReader(file)

        return Gson().fromJson(reader, Launches::class.java) ?: Launches()
    }

}
