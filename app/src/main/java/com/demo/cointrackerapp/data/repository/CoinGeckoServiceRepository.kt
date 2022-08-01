package com.demo.cointrackerapp.data.repository

import com.demo.cointrackerapp.data.local.Alerts
import com.demo.cointrackerapp.data.local.LocalDataSource
import com.demo.cointrackerapp.data.remote.RemoteDataSource
import javax.inject.Inject

class CoinGeckoServiceRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localeDataSource: LocalDataSource
) {

    suspend fun getPrices() =
        remoteDataSource.getPrices()

    suspend fun getHistory(id: String) = remoteDataSource.getHistory(id)

    suspend fun insertAlertsData(alerts: Alerts) = localeDataSource.insertAlertsData(alerts)

    fun fetchAlertsItems() = localeDataSource.fetchAlertsItems()

    suspend fun deleteAlerts(alerts: Alerts) = localeDataSource.deleteAlerts(alerts)


}