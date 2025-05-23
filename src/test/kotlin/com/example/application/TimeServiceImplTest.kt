package com.example.application

import com.example.domain.LocalizedTime
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class TimeServiceImplTest {

    private val service = TimeServiceImpl()

    @Test
    fun `getCurrentTimeForCountryCode returns correct time for FR`() {
        val result = service.getCurrentTimeForCountryCode("FR")
        expectThat(result).isNotNull()
        expectThat(result?.timeZone).isEqualTo("Europe/Paris")
        // Check that the time is somewhat recent and formatted correctly
        val expectedTime = ZonedDateTime.now(ZoneId.of("Europe/Paris")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        expectThat(result?.currentTime?.substringBeforeLast(":")).isEqualTo(expectedTime.substringBeforeLast(":")) // Compare up to minutes
    }

    @Test
    fun `getCurrentTimeForCountryCode returns correct time for GB`() {
        val result = service.getCurrentTimeForCountryCode("GB")
        expectThat(result).isNotNull()
        expectThat(result?.timeZone).isEqualTo("Europe/London")
        val expectedTime = ZonedDateTime.now(ZoneId.of("Europe/London")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        expectThat(result?.currentTime?.substringBeforeLast(":")).isEqualTo(expectedTime.substringBeforeLast(":")) // Compare up to minutes
    }
    
    @Test
    fun `getCurrentTimeForCountryCode handles lowercase country codes`() {
        val resultFR = service.getCurrentTimeForCountryCode("fr")
        expectThat(resultFR).isNotNull()
        expectThat(resultFR?.timeZone).isEqualTo("Europe/Paris")

        val resultGB = service.getCurrentTimeForCountryCode("gb")
        expectThat(resultGB).isNotNull()
        expectThat(resultGB?.timeZone).isEqualTo("Europe/London")
    }

    @Test
    fun `getCurrentTimeForCountryCode returns null for invalid country code`() {
        val result = service.getCurrentTimeForCountryCode("XX")
        expectThat(result).isNull()
    }
}
