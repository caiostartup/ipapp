package com.hyqoo.ipapp.domain

import com.hyqoo.ipapp.data.repository.IpDataRepository
import com.hyqoo.ipapp.model.IpData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIpDataUseCase @Inject constructor(
    private val ipDataRepository: IpDataRepository
) {
    /**
     * Returns a Ip Data with their associated followed state.
     *
     * @param ip - the field used search the data.
     */
    suspend operator fun invoke(ip: String): Flow<IpData?> =
        ipDataRepository.getIpData(ip)

}
