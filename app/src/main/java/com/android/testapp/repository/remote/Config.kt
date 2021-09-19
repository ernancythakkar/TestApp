package com.android.testapp.repository.remote

/**
 * Server configuration.
 */
object Config {
    /**
     * Base url for server
     */
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    /**
     * Api resource base url
     */
    const val API_URL = "$BASE_URL/"

    /**
     * Timeout in milliseconds
     */
    const val TIMEOUT_IN_MILLISECONDS: Long = 10000
}