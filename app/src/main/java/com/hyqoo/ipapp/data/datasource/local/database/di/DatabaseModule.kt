package com.hyqoo.ipapp.data.datasource.local.database.di

import android.content.Context
import androidx.room.Room
import com.hyqoo.ipapp.data.datasource.local.database.IpDataDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesIpDataDatabase(
        @ApplicationContext context: Context,
    ): IpDataDatabase = Room.databaseBuilder(
        context,
        IpDataDatabase::class.java,
        "ip-data-db",
    ).build()
}
