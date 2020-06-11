package com.zubak.spacex.holder

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zubak.spacex.data.Launch
import kotlinx.android.synthetic.main.launch_holder.view.*

class LaunchHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d(LaunchHolder::javaClass.toString(), "click on launch holder")
    }

    fun initialize(launch: Launch) {
        view.launch_name.text = launch.missionName
    }
}
