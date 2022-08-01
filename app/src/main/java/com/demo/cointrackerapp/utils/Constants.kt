package com.demo.cointrackerapp.utils

class Constants {

    companion object {

        const val BASE_URL = "https://api.coingecko.com/api/v3/"
        const val  PRICE = "simple/price"
        const val HISTORY = "coins/{id}/market_chart"
        const val ALERT_SERVICE_CALL = 20000L
    }
}