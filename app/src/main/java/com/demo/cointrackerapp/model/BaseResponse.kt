package com.demo.cointrackerapp.model

import com.google.gson.annotations.SerializedName


data class BaseResponse(

    @SerializedName("bitcoin") val bitcoin: Bitcoin,
    @SerializedName("ethereum") val ethereum: Ethereum,
    @SerializedName("ripple") val ripple: Ripple

)