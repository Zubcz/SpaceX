package com.zubak.spacex.ui.launches

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zubak.spacex.api.LaunchesType
import com.zubak.spacex.core.DataManager
import com.zubak.spacex.data.Launches

class LaunchesViewModel(
    private val context: Context,
    private val launchesType: LaunchesType
) : ViewModel() {

    var launches: MutableLiveData<Launches> = MutableLiveData()

    init {
        DataManager().getLaunches(launchesType, context, launches)
    }

    fun refreshLaunches() {
        DataManager().getLaunches(launchesType, context, launches)
    }

}
