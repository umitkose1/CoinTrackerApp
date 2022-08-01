package com.demo.cointrackerapp.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import com.demo.cointrackerapp.R
import com.demo.cointrackerapp.data.local.Alerts
import com.demo.cointrackerapp.data.repository.CoinGeckoServiceRepository
import com.demo.cointrackerapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class NotificationService
    : Service() {
    private val channelId = "Price Alert Notification"

    @Inject
    lateinit var repository: CoinGeckoServiceRepository


    private lateinit var allAlerts: LiveData<List<Alerts>>

    private lateinit var allAlertsList: List<Alerts>


    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
            showServiceNotification()
            allAlerts = repository.fetchAlertsItems()
            allAlerts.observeForever {
                allAlertsList = it
            }

        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                repository.getPrices().data.let {
                    if (!allAlertsList.isNullOrEmpty()) {
                        for (item in allAlertsList) {
                            when (item.itemName) {
                                "btc" -> {
                                    if (it!!.bitcoin.usd > item.itemMaxValue) {
                                        showAlertNotification(
                                            item.itemName,
                                            item.itemMaxValue,
                                            true
                                        )
                                        repository.deleteAlerts(item)
                                    } else if (it.bitcoin.usd < item.itemMinValue) {
                                        showAlertNotification(
                                            item.itemName,
                                            item.itemMinValue,
                                            false
                                        )
                                        repository.deleteAlerts(item)
                                    }
                                }
                                "eth" -> {
                                    if (it!!.ethereum.usd > item.itemMaxValue) {
                                        showAlertNotification(
                                            item.itemName,
                                            item.itemMaxValue,
                                            true
                                        )
                                        repository.deleteAlerts(item)
                                    } else if (it.ethereum.usd < item.itemMinValue) {
                                        showAlertNotification(
                                            item.itemName,
                                            item.itemMinValue,
                                            false
                                        )
                                        repository.deleteAlerts(item)
                                    }
                                }
                                "xrp" -> {
                                    if (it!!.ripple.usd > item.itemMaxValue) {
                                        showAlertNotification(
                                            item.itemName,
                                            item.itemMaxValue,
                                            true
                                        )
                                        repository.deleteAlerts(item)
                                    } else if (it.ripple.usd < item.itemMinValue) {
                                        showAlertNotification(
                                            item.itemName,
                                            item.itemMinValue,
                                            false
                                        )
                                        repository.deleteAlerts(item)
                                    }
                                }
                            }
                        }
                    }

                }
                delay(Constants.ALERT_SERVICE_CALL)
            }
        }

        return START_STICKY
    }


    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    private fun showServiceNotification() {
        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Coin Tacker Service")
            .setSmallIcon(R.drawable.add_alarm)
            .build()
        startForeground(1, notification)
    }

    private fun showAlertNotification(itemName: String, itemValue: Float, higherOrLower: Boolean) {

        val text = if (higherOrLower) {
            "$itemName is higher than $itemValue"
        } else {
            "$itemName is lower than $itemValue"
        }
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.add_alarm)
                .setContentTitle("Coin Price Alert")
                .setContentText(text)
                .setAutoCancel(true)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt(100)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }
}
