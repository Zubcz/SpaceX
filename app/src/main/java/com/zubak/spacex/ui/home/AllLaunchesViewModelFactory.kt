package com.zubak.spacex.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zubak.spacex.api.LaunchesType

class AllLaunchesViewModelFactory(
    private val context: Context?,
    private val launchesType: LaunchesType
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(Context::class.java, LaunchesType::class.java)
            .newInstance(context, launchesType)
    }
}
