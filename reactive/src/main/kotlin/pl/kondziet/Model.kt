package pl.kondziet

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Flight(
    val id: String,
    val price: Double,
    val durationMinutes: Int,
    val isDirect: Boolean
)

@Serdeable
data class Hotel(
    val id: String,
    val price: Double,
    val stars: Int
)

@Serdeable
data class ScoredPackage(
    val flightId: String,
    val hotelId: String,
    val totalCost: Double,
    val score: Double
)