package com.hyqoo.ipapp.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hyqoo.ipapp.data.datasource.local.database.dao.IpDataDao
import com.hyqoo.ipapp.data.datasource.local.database.model.IpDataEntity
import com.hyqoo.ipapp.data.datasource.local.database.util.DateConverters
import com.hyqoo.ipapp.data.datasource.local.database.util.InstantConverter

@Database(
    entities = [
        IpDataEntity::class,
    ],
    version = 1,
    autoMigrations = [],
    exportSchema = true,
)
@TypeConverters(
    DateConverters::class,
    InstantConverter::class
)
abstract class IpDataDatabase : RoomDatabase() {
    abstract fun ipDataDao(): IpDataDao
}
