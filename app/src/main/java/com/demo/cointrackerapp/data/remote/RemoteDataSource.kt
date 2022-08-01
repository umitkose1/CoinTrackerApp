package com.demo.cointrackerapp.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val coinGeckoService: CoinGeckoService) :
    BaseDataSource() {

    suspend fun getPrices() =
        getResult { coinGeckoService.getPrices() }

    suspend fun getHistory(id: String) = getResult {
        coinGeckoService.getHistory(id)
    }
}