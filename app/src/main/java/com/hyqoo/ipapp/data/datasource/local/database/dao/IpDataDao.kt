package com.hyqoo.ipapp.data.datasource.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.hyqoo.ipapp.data.datasource.local.database.model.IpDataEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [IpDataEntity] access
 */
@Dao
interface IpDataDao {
    @Query(
        value = """
        SELECT * FROM ips_data
        WHERE ip = :ip
    """,
    )
    fun getIpDataEntity(ip: String): Flow<IpDataEntity?>

    @Query(value = "SELECT * FROM ips_data")
    fun getIpDataEntities(): Flow<List<IpDataEntity>>

    @Query(
        value = """
        SELECT * FROM ips_data
        WHERE ip IN (:ips)
    """,
    )
    fun getIpDataEntities(ips: Set<String>): Flow<List<IpDataEntity>>

    /**
     * Inserts [ipDataEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceIpDatas(ipDataEntities: List<IpDataEntity>): List<Long>

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Upsert
    suspend fun upsertIpDatas(entity: IpDataEntity)

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM ips_data
            WHERE ip in (:ips)
        """,
    )
    suspend fun deleteIpDatas(ips: List<String>)
}
