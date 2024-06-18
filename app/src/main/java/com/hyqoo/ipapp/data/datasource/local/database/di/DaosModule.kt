package com.hyqoo.ipapp.data.datasource.local.database.di

import com.hyqoo.ipapp.data.datasource.local.database.IpDataDatabase
import com.hyqoo.ipapp.data.datasource.local.database.dao.IpDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesIpDataDao(
        database: IpDataDatabase,
    ): IpDataDao = database.ipDataDao()

}
