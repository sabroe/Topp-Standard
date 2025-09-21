package com.yelstream.topp.grasp.cascade.plugin;

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class BuildTime constructor(
        val buildZonedTime: ZonedDateTime,
        val buildTimeZulu: String,
        val buildTimeOffset: String,
        val buildTimeZoned: String
) {
    val buildTime: String = buildTimeZulu  // Simple delegation
    val sanitizedBuildTime: String = buildTimeZulu.replace("T", "-").replace(":", "")

    companion object {
        operator fun invoke(zonedTime: ZonedDateTime): BuildTime {
            return BuildTime(
                buildZonedTime = zonedTime,
                buildTimeZulu = DateTimeFormatter.ISO_INSTANT.format(zonedTime), // E.g., '2011-12-03T10:15:30Z'
                buildTimeOffset = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedTime), // E.g., '2011-12-03T10:15:30+01:00'
                buildTimeZoned = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedTime) // E.g., '2011-12-03T10:15:30+01:00[Europe/Copenhagen]'
            )
        }

        fun now(): BuildTime = invoke(ZonedDateTime.now())
    }

    fun debugInfo(): String = """
        BuildTime Debug:
        ├── Time-Zulu: $buildTimeZulu
        ├── Time-Offset: $buildTimeOffset
        ├── Time-Zoned: $buildTimeZoned
        ├── Time: $buildTime
        └── Time-Sanitized: $sanitizedBuildTime
    """.trimIndent()

    fun populate(setter: (String, String) -> Unit) {
        setter.invoke("build-time-zulu", buildTimeZulu)
        setter.invoke("build-time-offset", buildTimeOffset)
        setter.invoke("build-time-zoned", buildTimeZoned)
        setter.invoke("build-time", buildTime)
        setter.invoke("sanitized-build-time", sanitizedBuildTime)
    }
}
