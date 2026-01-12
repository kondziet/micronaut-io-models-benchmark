package pl.kondziet

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client(id = "upstream-client")
interface UpstreamClient {

    @Get("/flights/premium")
    suspend fun getPremiumFlights(): List<Flight>

    @Get("/flights/budget")
    suspend fun getBudgetFlights(): List<Flight>

    @Get("/hotels")
    suspend fun getHotels(): List<Hotel>
}