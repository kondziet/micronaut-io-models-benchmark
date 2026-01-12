package pl.kondziet

import jakarta.inject.Singleton
import kotlin.collections.asSequence

@Singleton
class DealProcessor {

    fun findBestPackages(
        flights: List<Flight>,
        hotels: List<Hotel>,
        budget: Double
    ): List<ScoredPackage> {

        // Sequences are lazy. No work is done until .toList() is called.
        return flights.asSequence()
            .flatMap { flight ->
                hotels.asSequence().map { hotel -> flight to hotel }
            }
            // 1. Efficiency: Filter raw primitives before object creation overhead
            .filter { (flight, hotel) ->
                (flight.price + hotel.price) <= budget
            }
            // 2. Efficiency: Simple Rating (Avoid heavy Math.pow or allocation)
            .map { (flight, hotel) ->
                val totalCost = flight.price + hotel.price

                val score = (1.0 - totalCost / budget) +
                        (1.0 - flight.durationMinutes / 1000.0) +
                        (hotel.stars * 0.2)

                ScoredPackage(flight.id, hotel.id, totalCost, score)
            }
            .sortedByDescending { it.score }
            .take(10)
            .toList()
    }
}