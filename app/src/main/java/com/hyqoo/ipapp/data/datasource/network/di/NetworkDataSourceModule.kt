package com.hyqoo.ipapp.data.datasource.network.di

import com.hyqoo.ipapp.data.datasource.network.IpDataNetworkDataSource
import com.hyqoo.ipapp.data.datasource.network.RetrofitIpDataNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {

    @Binds
    internal abstract fun bindsIpDataNetworkDataSourceModule(
        ipDataNetworkDataSource: RetrofitIpDataNetwork,
    ): IpDataNetworkDataSource

}
