package com.demo.cointrackerapp.data.remote

import com.demo.cointrackerapp.model.BaseResponse
import com.demo.cointrackerapp.model.CoinHistoryResponse
import com.demo.cointrackerapp.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface CoinGeckoService {

    @GET(Constants.PRICE)
    suspend fun getPrices(
        @Query("ids") ids: String = "bitcoin,ethereum,ripple",
        @Query("vs_currencies") currencies: String = "USD"
    ): Response<BaseResponse>

    @GET(Constants.HISTORY)
    suspend fun getHistory(
        @Path("id") id:String,
        @Query("vs_currency") currency: String = "USD",
        @Query("days") days: String = "7",
        @Query("interval") interval: String = "daily"
    ): Response<CoinHistoryResponse>
}