package com.demo.cointrackerapp.model

import com.google.gson.annotations.SerializedName

data class Ripple(
    @SerializedName("usd") val usd: Double
)