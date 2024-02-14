package com.example.nflstats.network
import com.example.nflstats.model.Entity
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://sports.core.api.espn.com/v2/sports/football/leagues/nfl/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface ESPNAPIService {
    @GET("{season}/types/2/{type}/{id}/statistics?lang=en&region=us/")
    fun fetchStatValues(@Path("season") season : Int, @Path("type") type : String, @Path("id") id : Int) : Map<String, Pair<Double, String>>
}

object ESPNApi {
    val servicer: ESPNAPIService by lazy {retrofit.create(ESPNAPIService::class.java)}
}