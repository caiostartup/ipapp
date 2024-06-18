package com.hyqoo.ipapp.data.datasource.network

import com.hyqoo.ipapp.BuildConfig.IP_API_URL
import com.hyqoo.ipapp.model.IpData
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [Retrofit] backed [RetrofitIpDataNetwork]
 */
@Singleton
internal class RetrofitIpDataNetwork @Inject constructor(
    json: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : IpDataNetworkDataSource {

    private val networkApi =
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(IP_API_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(IpDataNetworkApi::class.java)

    override suspend fun getIpData(ip: String): IpData {
        return networkApi.getIpData(ip).body()!!
    }

}