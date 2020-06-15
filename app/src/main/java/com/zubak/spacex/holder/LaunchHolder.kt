package com.zubak.spacex.holder

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zubak.spacex.R
import com.zubak.spacex.core.TAG
import com.zubak.spacex.data.Launch
import com.zubak.spacex.ui.launchDetail.LaunchDetailFragment
import com.zubak.spacex.ui.launches.LaunchesFragment
import kotlinx.android.synthetic.main.launch_holder.view.*


class LaunchHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private var context: Context? = null
    private var launch: Launch? = null

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        try {
            val activity = context as FragmentActivity
            val f: Fragment? =
                activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            Log.e(TAG, f!!.TAG)
            launch?.let {
                activity.supportFragmentManager.beginTransaction()
                    .add(
                        R.id.nav_host_fragment,
                        LaunchDetailFragment(launch!!),
                        LaunchesFragment::class.simpleName
                    )
                    .addToBackStack(LaunchesFragment::class.simpleName).commit();
            }
        } catch (e: ClassCastException) {
            Log.e(TAG, "Unable to get the fragment manager")
        }
        Log.d(LaunchHolder::javaClass.toString(), "click on launch holder")
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
