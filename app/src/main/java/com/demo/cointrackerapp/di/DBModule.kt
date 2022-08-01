package com.demo.cointrackerapp.di

import android.content.Context
import com.demo.cointrackerapp.data.local.AlertsDB
import com.demo.cointrackerapp.data.local.AlertsDao
import com.demo.cointrackerapp.data.local.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DBModule {

    @Provides
    fun provideFavoritesDao(@ApplicationContext appContext: Context): AlertsDao {
        return AlertsDB.getInstance(appContext).alertsDao
    }

    @Provides
    fun provideFavoritesDBRepository(alertsDao: AlertsDao) = LocalDataSource(alertsDao)

}