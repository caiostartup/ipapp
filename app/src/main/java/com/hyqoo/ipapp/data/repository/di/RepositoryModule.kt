package com.hyqoo.ipapp.data.repository.di

import com.hyqoo.ipapp.data.repository.IpDataRepository
import com.hyqoo.ipapp.data.repository.OfflineFirstIpDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindsIpDataRepository(
        ipDataRepository: OfflineFirstIpDataRepository,
    ): IpDataRepository

}
