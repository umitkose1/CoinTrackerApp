package com.demo.cointrackerapp.data.local
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val alertsDao: AlertsDao) {
    suspend fun insertAlertsData(alerts: Alerts) = alertsDao.insert(alerts)

     fun fetchAlertsItems() = alertsDao.fetch()

    suspend fun deleteAlerts(alerts: Alerts) = alertsDao.delete(alerts)
}