package com.zubak.spacex.core


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.zubak.spacex.R
import com.zubak.spacex.api.LaunchApi
import com.zubak.spacex.api.LaunchesType
import com.zubak.spacex.data.Launches
import com.zubak.spacex.service.RetrofitService
import com.zubak.spacex.ui.filters.Filters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*


class DataManager {
    private var apiInterface = RetrofitService().getClient().create(LaunchApi::class.java)

    fun getLaunches(
        launchesType: LaunchesType
        , context: Context
        , launches: MutableLiveData<Launches>
    ) {
        val call: Call<Launches>? = when (launchesType) {
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
                val dataHash = launches.value.toString().sha1()
                if (dataHash != getStoredDataHash(launchesType, context)) {
                    storeData(launchesType, context, launches)
                    storeDataHash(launchesType, context, dataHash)
                }
            }

            override fun onFailure(call: Call<Launches>, t: Throwable?) {
                call.cancel()
                val dataHash = launches.value.toString().sha1()
                if (dataHash != getStoredDataHash(launchesType, context)) {
                    launches.value = getStoredData(launchesType, context)
                }
                Toast.makeText(context, R.string.no_internet, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun storeData(
        launchesType: LaunchesType
        , context: Context
        , launches: MutableLiveData<Launches>
    ) {
        try {
            val outputStreamWriter = OutputStreamWriter(
                context.openFileOutput(
                    "$launchesType.json"
                    , Context.MODE_PRIVATE
                )
            )
            outputStreamWriter.write(Gson().toJson(launches.value))
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
    }

    fun getStoredData(launchesType: LaunchesType, context: Context): Launches {
        var launches: Launches? = null
        try {
            val inputStream: InputStream? = context.openFileInput("$launchesType.json")
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                launches = Gson().fromJson(inputStreamReader, Launches::class.java)
            }
        } catch (e: FileNotFoundException) {
            Log.e(DataManager::class.java.toString(), "File not found: $e")
        } catch (e: IOException) {
            Log.e(DataManager::class.java.toString(), "Can not read file: $e")
        }
        return launches ?: Launches()
    }

    private fun storeDataHash(
        launchesType: LaunchesType
        , context: Context
        , hash: String
    ) {
        val preferencesName = context.resources.getString(R.string.preferences)
        context
            .getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
            .edit()
            .putString(launchesType.toString(), hash)
            .apply()
    }

    private fun getStoredDataHash(
        launchesType: LaunchesType
        , context: Context
    ): String {
        val preferencesName = context.resources.getString(R.string.preferences)
        return context
            .getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
            .getString(launchesType.toString(), "") ?: ""
    }

    fun storeFilter(filters: Filters, context: Context) {
        val preferencesName = context.resources.getString(R.string.preferences)
        context
            .getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
            .edit()
            .putString(context.getString(R.string.filters_preferences), filters.toString())
            .apply()
    }

    fun getFilter(context: Context): Filters {
        val preferencesName = context.resources.getString(R.string.preferences)
        return Filters.valueOf(
            context
                .getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
                .getString(context.getString(R.string.filters_preferences), null)
                ?: Filters.MISSION_NAME.name
        )
    }

}
