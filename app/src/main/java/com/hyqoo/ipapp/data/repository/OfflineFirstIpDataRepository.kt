package com.hyqoo.ipapp.data.repository

import com.hyqoo.ipapp.data.datasource.local.database.dao.IpDataDao
import com.hyqoo.ipapp.data.datasource.local.database.model.asDto
import com.hyqoo.ipapp.data.datasource.network.IpDataNetworkDataSource
import com.hyqoo.ipapp.model.IpData
import com.hyqoo.ipapp.model.asEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

class OfflineFirstIpDataRepository @Inject constructor(
    private val ipDataDao: IpDataDao,
    private val network: IpDataNetworkDataSource,
): IpDataRepository {

    override fun getIpData(ip: String): Flow<IpData?> {
        val localIpData = ipDataDao.getIpDataEntity(ip).map { it?.asDto() }
        localIpData.getValueOrNull()?.let {
            val fiveMinutesAhead = it.lastUpdate + 5.minutes
            if(fiveMinutesAhead > Clock.System.now()){
                return localIpData
            }
        }
        runBlocking(Dispatchers.IO) {
            val remoteIpData = network.getIpData(ip)
            ipDataDao.upsertIpDatas(remoteIpData.asEntity())
            return@runBlocking ipDataDao.getIpDataEntity(ip).map { it?.asDto() }
        }.run {
            return flowOf(null)
        }
    }
}

fun <T> Flow<T>.getValueOrNull(): T? {
    runBlocking(Dispatchers.Default) {
        return@runBlocking this@getValueOrNull.firstOrNull()
    }.run {
        return null
    }
}

