package com.demo.cointrackerapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.demo.cointrackerapp.data.local.Alerts
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun <T> LiveData<T>.getValueBlocking(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)

    val observer = Observer<T> { t ->
        value = t
        latch.countDown()
    }

    observeForever(observer)

    latch.await(2, TimeUnit.SECONDS)
    return value
}

@RunWith(AndroidJUnit4::class)
open class AlertsDaoTest : RoomDatabaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertFavoriteTest() {
        val alert =
            Alerts("btc", 1, 100)
        runBlocking {
            appDatabase.alertsDao.insert(alert)

        }
        val quotesSize = appDatabase.alertsDao.fetch().getValueBlocking()?.size
        assertEquals(quotesSize, 1)
    }

    @Test
    fun deleteFavoriteTest() {
        val alert =
            Alerts("btc", 1, 100)
        runBlocking {
            appDatabase.alertsDao.delete(alert)
        }
        assertEquals(appDatabase.alertsDao.fetch().getValueBlocking()?.size, 0)
    }

    @Test
    fun getFavoritesAsLiveDataTest() {
        val alert =
            Alerts("btc", 1, 100)
        runBlocking {
            appDatabase.alertsDao.insert(alert)
        }
        val quoteLiveDataValue = appDatabase.alertsDao.fetch().getValueBlocking()
        assertEquals(quoteLiveDataValue?.size, 1)
    }
}