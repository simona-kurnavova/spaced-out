package com.kurnavova.spacedout.data.spaceflights.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Initializes a Retrofit API instance.
 */
internal object SpaceFlightApiInitializer {

    /**
     * Creates a Retrofit instance.
     *
     * @return The Retrofit instance.
     */
    fun create(): SpaceFlightApi {
        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SpaceFlightApi::class.java)
    }
}

private const val BASE_URL = "https://api.spaceflightnewsapi.net/v4/"
private const val CONNECTION_TIMEOUT = 10L
