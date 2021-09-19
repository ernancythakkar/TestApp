package com.android.testapp.repository.remote

import com.android.testapp.repository.remote.Config.TIMEOUT_IN_MILLISECONDS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Class to build [API] client for retrofit server calls.
 * All the api calls would be made through this client.
 */
object RestClient {

    /**
     * Api client.
     */
    private var apiClient: API? = null

    /**
     * Builds and returns the api client.
     *
     * @return [API] client to make server calls.
     */
    fun get(): API {
        if (apiClient == null) {
            apiClient = Retrofit.Builder()
                .baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient().newBuilder()
                        .connectTimeout(TIMEOUT_IN_MILLISECONDS, TimeUnit.MILLISECONDS)
                        .readTimeout(TIMEOUT_IN_MILLISECONDS, TimeUnit.MILLISECONDS)
                        .writeTimeout(TIMEOUT_IN_MILLISECONDS, TimeUnit.MILLISECONDS)
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                        .build()
                ).build().create(API::class.java)
        }
        return apiClient!!
    }
}