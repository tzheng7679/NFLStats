package com.example.nflstats.network
import com.example.nflstats.model.json.EntityStats
import retrofit2.http.Path
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://sports.core.api.espn.com/v2/sports/football/leagues/nfl/seasons/"
val contentType = "application/json".toMediaType()

val intercepter = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}
val client = OkHttpClient.Builder().apply {
    this.addInterceptor(intercepter)
        // time out setting
        .connectTimeout(3,TimeUnit.SECONDS)
        .readTimeout(20,TimeUnit.SECONDS)
        .writeTimeout(25,TimeUnit.SECONDS)

}.build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

interface ESPNAPIService {
    @GET("{season}/types/2/{type}/{id}/statistics?lang=en&region=us/")
    suspend fun fetchStatValues(@Path("season") season : Int,
                                @Path("type") type : String,
                                @Path("id") id : Int)
    : String

    @GET("{season}/teams/{teamID}/athletes?lang=en&region=us&limit=200/")
    suspend fun fetchPlayers(@Path("season") season : Int,
                             @Path("teamID") teamID : Int)
    : String

    @GET
    suspend fun fetchPlayerInfo(@Url url : String)
    : String
}

object ESPNApi {
    val servicer: ESPNAPIService by lazy {retrofit.create(ESPNAPIService::class.java)}
}