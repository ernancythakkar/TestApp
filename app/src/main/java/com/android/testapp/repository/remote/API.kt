package com.android.testapp.repository.remote

import com.android.testapp.repository.model.ModelAlbum
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface for making REST api calls.
 */
interface API {

    /**
     * GET request to fetch albums from server.
     *
     * @return List of [ModelAlbum]
     */
    @GET("albums")
    suspend fun getAlbums(): Response<List<ModelAlbum>>
}