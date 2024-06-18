package com.hyqoo.ipapp.data.datasource.network

import com.hyqoo.ipapp.model.IpData

/**
 * Interface representing network calls to the backend
 */
interface IpDataNetworkDataSource {
    suspend fun getIpData(ip: String): IpData

}