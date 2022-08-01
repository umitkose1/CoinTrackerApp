package com.demo.cointrackerapp.model

import com.google.gson.annotations.SerializedName

data class CoinHistoryResponse(

    @SerializedName("prices") val prices: List<List<Float>>,
    @SerializedName("market_caps") val market_caps: List<List<Float>>,
    @SerializedName("total_volumes") val total_volumes: List<List<Float>>
)