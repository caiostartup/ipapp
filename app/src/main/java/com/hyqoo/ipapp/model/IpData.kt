package com.hyqoo.ipapp.model

import com.hyqoo.ipapp.data.datasource.local.database.model.IpDataEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IpData(
    @SerialName(value = "query")
    val ip: String,
    val status: String?,
    val country: String?,
    val countryCode: String?,
    val region: String?,
    val regionName: String?,
    val city: String?,
    val zip: String?,
    val lat: Float?,
    val lon: Float?,
    val timezone: String?,
    val isp: String?,
    val org: String?,
    @SerialName(value = "as")
    val autonomousSystem: String?,
    val message: String? = null,
    var lastUpdate: Instant = Clock.System.now()
)


fun IpData.asEntity() = IpDataEntity(
    ip = ip,
    status = status,
    country = country,
    countryCode = countryCode,
    region = region,
    regionName = regionName,
    city = city,
    zip = zip,
    lat = lat,
    lon = lon,
    timezone = timezone,
    isp = isp,
    org = org,
    autonomousSystem = autonomousSystem,
    lastUpdate = lastUpdate
)
