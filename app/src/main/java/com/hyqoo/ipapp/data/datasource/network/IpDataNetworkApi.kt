package com.hyqoo.ipapp.data.datasource.network

import com.hyqoo.ipapp.model.IpData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit service object for creating api calls
 */
interface IpDataNetworkApi {
    @GET("json/{ip}")
    suspend fun getIpData(@Path("ip") ip: String): Response<IpData>
}