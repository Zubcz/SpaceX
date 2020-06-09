package com.zubak.spacex.data


import com.google.gson.annotations.SerializedName

data class SecondStage(
    @SerializedName("block")
    val block: Any?,
    @SerializedName("payloads")
    val payloads: List<Payload>?
)