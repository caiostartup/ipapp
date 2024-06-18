package com.hyqoo.ipapp.data.repository

import com.hyqoo.ipapp.model.IpData
import kotlinx.coroutines.flow.Flow

interface IpDataRepository  {

    /**
     * Gets the available IpData as a stream
     */
    suspend fun getIpData(ip: String): Flow<IpData?>

}