package com.android.testapp.repository

import android.app.Application
import com.android.testapp.repository.local.PrefsManager
import com.android.testapp.repository.remote.API
import com.android.testapp.repository.remote.RestClient
import com.android.testapp.repository.remote.ResultWrapper
import com.android.testapp.repository.remote.SafeCallGenerator
import com.android.testapp.repository.model.ModelAlbum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Single source of truth for albums data.
 *
 * @param webService Remote source for albums.
 * @param prefsManager Local cache source for albums.
 */
class AlbumRepository private constructor(
    private val webService: API,
    private val prefsManager: PrefsManager
) {

    companion object {
        /**
         * Single instance of [AlbumRepository].
         */
        private var INSTANCE: AlbumRepository? = null

        /**
         * Returns instance for [AlbumRepository]. Creates a new instance if instance is not found.
         *
         * @param context Application instance.
         */
        fun getInstance(context: Application): AlbumRepository {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =
                    AlbumRepository(RestClient.get(), PrefsManager.getInstance(context))
                INSTANCE = instance
                return instance
            }
        }
    }

    /**
     * Returns albums from the repository.
     *
     * @param onSuccess Callback function for returning albums successfully.
     * @param onError Callback function for returning error if failed to fetch albums.
     */

    fun getAlbums(onSuccess: (albums: List<ModelAlbum>) -> Unit, onError: (error: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            //Call remote api in IO thread
            val response = fetchAlbumsApi()
            withContext(Dispatchers.Main) {
                //Handle result in main thread
                when (response) {
                    is ResultWrapper.NetworkError -> {
                        //Check if cache has data, else return error
                        val cacheData = prefsManager.getAlbumFromCache()
                        when {
                            cacheData.isEmpty() -> onError("Network error")
                            else -> onSuccess(cacheData)
                        }
                    }
                    is ResultWrapper.GenericError -> {
                        //Check if cache has data, else return error
                        val cacheData = prefsManager.getAlbumFromCache()
                        when {
                            cacheData.isEmpty() -> onError(
                                response.error?.getErrorMessage() ?: "Unknown error"
                            )
                            else -> onSuccess(cacheData)
                        }
                    }
                    is ResultWrapper.Success -> response.value.body()?.let { albums ->
                        //Return the album data and update cache
                        prefsManager.saveAlbumsIntoCache(albums)
                        onSuccess.invoke(albums)
                    }
                }
            }
        }
    }

    /**
     * Internal function to fetch alums from server.
     */
    private suspend fun fetchAlbumsApi(): ResultWrapper<Response<List<ModelAlbum>>> {
        return SafeCallGenerator.safeApiCall(Dispatchers.IO) {
            webService.getAlbums()
        }
    }
}