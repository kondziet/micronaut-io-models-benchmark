package pl.kondziet

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@Controller("/gateway")
class GatewayController(
    private val client: UpstreamClient,
    private val processor: DealProcessor,
    private val dealProcessor: DealProcessor
) {

    @Get("/{budget}")
    suspend fun findDeals(@PathVariable budget: Double): List<ScoredPackage> = coroutineScope {
        val premium = async { client.getPremiumFlights() }
        val budgetDef = async { client.getBudgetFlights() }
        val hotelsDef = async { client.getHotels() }

        val allFlights = premium.await() + budgetDef.await()
        val hotels = hotelsDef.await()

        dealProcessor.findBestPackages(allFlights, hotels, budget)
    }
}