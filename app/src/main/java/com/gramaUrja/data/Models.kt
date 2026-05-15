package com.gramaUrja.data

data class FeedItem(val name: String, val isPowerOn: Boolean, val time: String)

data class Zone(
    val name: String,
    val transformer: String,
    val taluk: String,
    val activeFarmers: Int,
    val status: ZoneStatus
)

data class AlertItem(val title: String, val subtitle: String, val time: String, val type: AlertType)

data class CropInfo(val name: String, val duration: String, val note: String, val recommended: String)

enum class PowerState { ON, OFF }
enum class ZoneStatus { ON, OFF, UNKNOWN }
enum class AlertType  { POWER_ON, POWER_OFF, SCHEDULED }
