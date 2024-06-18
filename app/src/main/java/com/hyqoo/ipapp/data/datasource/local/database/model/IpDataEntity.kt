package com.hyqoo.ipapp.data.datasource.local.database.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hyqoo.ipapp.model.IpData
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Entity(
    tableName = "ips_data",
)
class IpDataEntity(
    @PrimaryKey
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
    val autonomousSystem: String?,
    @NonNull
    var lastUpdate: Instant = Clock.System.now()
)

fun IpDataEntity.asDto() = IpData(
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
    autonomousSystem = autonomousSystem
)
