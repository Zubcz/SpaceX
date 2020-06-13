package com.zubak.spacex.holder

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zubak.spacex.R
import com.zubak.spacex.data.Launch
import kotlinx.android.synthetic.main.launch_holder.view.*

class LaunchHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d(LaunchHolder::javaClass.toString(), "click on launch holder")
    }

    @SuppressLint("SetTextI18n")
    fun initialize(launch: Launch, context: Context?) {
        view.widget_launch_mission.text = launch.missionName
        view.widget_launch_date.text =
            context?.getText(R.string.launch_time).toString() + " " + launch.launchDateLocal
        view.widget_launch_rocket.text =
            context?.getText(R.string.launch_vehicle).toString() + " " + launch.rocket?.rocketName
        view.widget_location.text =
            context?.getText(R.string.launch_location)
                .toString() + " " + launch.launchSite?.siteName

        context?.let {
            if (launch.links?.flickrImages?.size ?: 0 > 0) {
                Glide.with(context)
                    .load(Uri.parse(launch.links?.flickrImages?.get(1) as String))
                    .centerCrop()
                    .into(view.image_launch)
            } else {
                Glide.with(context)
                    .load(R.mipmap.rocket_holder)
                    .fitCenter()
                    .into(view.image_launch)
            }
        }
    }
}
