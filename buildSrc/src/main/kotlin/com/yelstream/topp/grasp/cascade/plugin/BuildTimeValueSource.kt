/*
 * Project: Topp Grasp
 * GitHub: https://github.com/sabroe/Topp-Grasp
 *
 * Copyright 2022-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.grasp.cascade.plugin

import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import java.time.DateTimeException
import java.time.Duration
import java.time.InstantSource
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Supplies the build time to Gradle as a value source.
 *
 * When the value is obtained at configuration time, Gradle treats it as a configuration-cache input:
 * on each build it obtains the value again and reuses the cache entry only if the value is unchanged.
 * The value holds both the instant and the time zone, so a change of either invalidates the cache entry.
 * The instant comes from an [InstantSource]: the system clock, optionally ticking in steps of [Parameters.tick].
 * With a tick, the instant stays the same within each step, so the cache entry can be reused within the step.
 */
abstract class BuildTimeValueSource : ValueSource<ZonedDateTime, BuildTimeValueSource.Parameters> {
    interface Parameters : ValueSourceParameters {
        /**
         * Step length as an ISO-8601 duration, for example `PT1M`, `PT1H` or `P1D`.
         * Steps are counted from the epoch, in UTC. Unset, blank or zero: no rounding.
         */
        val tick: Property<String>

        /**
         * Time zone ID, for example `UTC` or `Europe/Copenhagen`.
         * Unset or blank: the JVM's default time zone.
         */
        val zone: Property<String>
    }

    override fun obtain(): ZonedDateTime = ZonedDateTime.ofInstant(instantSource().instant(), zone())

    private fun instantSource(): InstantSource {
        val system = InstantSource.system()
        val tick = parameters.tick.orNull?.trim()?.takeIf { it.isNotEmpty() } ?: return system
        val duration = try {
            Duration.parse(tick)
        } catch (e: DateTimeException) {
            throw invalid(ChoreographyCascadePlugin.BUILD_TIME_TICK_PROPERTY_NAME, tick,
                "expected an ISO-8601 duration such as PT1M, PT1H or P1D", e)
        }
        return try {
            InstantSource.tick(system, duration)
        } catch (e: IllegalArgumentException) {
            throw invalid(ChoreographyCascadePlugin.BUILD_TIME_TICK_PROPERTY_NAME, tick, e.message ?: "", e)
        }
    }

    private fun zone(): ZoneId {
        val zone = parameters.zone.orNull?.trim()?.takeIf { it.isNotEmpty() } ?: return ZoneId.systemDefault()
        return try {
            ZoneId.of(zone)
        } catch (e: DateTimeException) {
            throw invalid(ChoreographyCascadePlugin.BUILD_TIME_ZONE_PROPERTY_NAME, zone,
                "expected a time zone ID such as UTC or Europe/Copenhagen", e)
        }
    }

    private fun invalid(name: String, value: String, reason: String, cause: Throwable) =
        GradleException("Invalid value '$value' for Gradle property '$name': $reason.", cause)
}
