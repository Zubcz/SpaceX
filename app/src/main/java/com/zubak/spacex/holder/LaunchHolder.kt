package com.zubak.spacex.holder

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.zubak.spacex.R
import com.zubak.spacex.data.Launch
import com.zubak.spacex.ui.launchDetail.LaunchDetailFragment
import kotlinx.android.synthetic.main.launch_holder.view.*


class LaunchHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private var context: Context? = null
    private var launch: Launch? = null

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val activity = context as FragmentActivity
        val fragment = LaunchDetailFragment()
        val args = Bundle()

        args.putString(activity.getString(R.string.launch_detail_bundle), Gson().toJson(launch))
        fragment.arguments = args

        launch?.let {
            activity.supportFragmentManager.beginTransaction()
                .add(
                    R.id.nav_host_fragment,
                    fragment
                )
                .addToBackStack(fragment.tag).commit();
        }
    }

    @SuppressLint("SetTextI18n")
    fun initialize(launch: Launch, context: Context?) {
        this.context = context
        this.launch = launch
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
                Glide.with(it)
                    .load(Uri.parse(launch.links?.flickrImages?.get(0) as String))
                    .centerCrop()
                    .into(view.image_launch)
            } else {
                Glide.with(it)
                    .load(R.mipmap.rocket_holder)
                    .fitCenter()
                    .into(view.image_launch)
            }
        }
    }
}
