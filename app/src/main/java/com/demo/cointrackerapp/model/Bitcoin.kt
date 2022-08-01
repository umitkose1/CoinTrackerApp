package com.demo.cointrackerapp.model

import com.google.gson.annotations.SerializedName


data class Bitcoin(
    @SerializedName("usd") val usd: Int
)