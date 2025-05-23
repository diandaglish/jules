package com.example.domain

interface TimeService {
    fun getCurrentTimeForCountryCode(countryCode: String): LocalizedTime?
}
