package com.kurnavova.spacedout.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Initializes a Retrofit API instance.
 */
object SpaceFlightApiInitializer {

    /**
     * Creates a Retrofit instance.
     *
     * @return The Retrofit instance.
     */
    fun create(): SpaceFlightApi {
        val client = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()

        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientBuilder = client.newBuilder().addInterceptor(interceptor)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SpaceFlightApi::class.java)
    }
}

private const val BASE_URL = "https://api.spaceflightnewsapi.net/v4/"
private const val CONNECTION_TIMEOUT = 10L
