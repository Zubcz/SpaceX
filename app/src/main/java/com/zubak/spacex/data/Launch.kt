package com.zubak.spacex.data


import com.google.gson.annotations.SerializedName

data class Launch(
    @SerializedName("details")
    val details: String?,
    @SerializedName("flight_number")
    val flightNumber: Int?,
    @SerializedName("is_tentative")
    val isTentative: Boolean?,
    @SerializedName("launch_date_local")
    val launchDateLocal: String?,
    @SerializedName("launch_date_unix")
    val launchDateUnix: Int?,
    @SerializedName("launch_date_utc")
    val launchDateUtc: String?,
    @SerializedName("launch_site")
    val launchSite: LaunchSite?,
    @SerializedName("launch_success")
    val launchSuccess: Any?,
    @SerializedName("launch_window")
    val launchWindow: Any?,
    @SerializedName("launch_year")
    val launchYear: String?,
    @SerializedName("links")
    val links: Links?,
    @SerializedName("mission_id")
    val missionId: List<Any>?,
    @SerializedName("mission_name")
    val missionName: String?,
    @SerializedName("rocket")
    val rocket: Rocket?,
    @SerializedName("ships")
    val ships: List<Any>?,
    @SerializedName("static_fire_date_unix")
    val staticFireDateUnix: Int?,
    @SerializedName("static_fire_date_utc")
    val staticFireDateUtc: String?,
    @SerializedName("tbd")
    val tbd: Boolean?,
    @SerializedName("telemetry")
    val telemetry: Telemetry?,
    @SerializedName("tentative_max_precision")
    val tentativeMaxPrecision: String?,
    @SerializedName("timeline")
    val timeline: Any?,
    @SerializedName("upcoming")
    val upcoming: Boolean?
)