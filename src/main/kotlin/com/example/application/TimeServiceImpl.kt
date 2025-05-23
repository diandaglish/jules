package com.example.application

import com.example.domain.LocalizedTime
import com.example.domain.TimeService
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class TimeServiceImpl : TimeService {

    private val countryCodeToTimeZoneId = mapOf(
        "FR" to "Europe/Paris",
        "GB" to "Europe/London"
    )

    override fun getCurrentTimeForCountryCode(countryCode: String): LocalizedTime? {
        val timeZoneIdString = countryCodeToTimeZoneId[countryCode.uppercase()]
            ?: return null // Country code not found in our map

        return try {
            val zoneId = ZoneId.of(timeZoneIdString)
            val currentDateTime = ZonedDateTime.now(zoneId)
            // Using ISO 8601 format for the time string, e.g., "2023-10-27T10:15:30+01:00[Europe/Paris]"
            // You can choose a simpler format if preferred, e.g., DateTimeFormatter.ISO_LOCAL_DATE_TIME
            val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME 
            LocalizedTime(
                timeZone = zoneId.toString(),
                currentTime = currentDateTime.format(formatter)
            )
        } catch (e: Exception) {
            // Catch potential issues with ZoneId.of() or ZonedDateTime.now(), though unlikely for valid IDs
            // Log the exception e
            null
        }
    }
}
