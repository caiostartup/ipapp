package com.hyqoo.ipapp.data.datasource.local.database.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
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
    @ColumnInfo(name = "ip")  val ip: String,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "countryCode") val countryCode: String?,
    @ColumnInfo(name = "region") val region: String?,
    @ColumnInfo(name = "regionName") val regionName: String?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "zip") val zip: String?,
    @ColumnInfo(name = "lat")  val lat: Float?,
    @ColumnInfo(name = "lon") val lon: Float?,
    @ColumnInfo(name = "timezone") val timezone: String?,
    @ColumnInfo(name = "isp") val isp: String?,
    @ColumnInfo(name = "org") val org: String?,
    @ColumnInfo(name = "autonomousSystem") val autonomousSystem: String?,
    @NonNull
    @ColumnInfo(name = "lastUpdate") var lastUpdate: Instant = Clock.System.now()
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
