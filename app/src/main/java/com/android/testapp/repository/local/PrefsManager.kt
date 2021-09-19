package com.android.testapp.repository.local

import android.app.Application
import android.content.Context
import com.android.testapp.repository.model.ModelAlbum
import com.google.gson.Gson

/**
 * Wrapper class for [android.content.SharedPreferences].
 *
 * @param context Context object.
 */
class PrefsManager private constructor(context: Context) {

    companion object {
        /**
         * Assigned name for SharedPreferences.
         */
        private const val PREFS_NAME = "testapp"

        /**
         * Key to store cache albums.
         */
        private const val KEY_ALBUM_CACHE = "key_album_cache"

        /**
         * Singleton instance for [PrefsManager].
         */
        private var INSTANCE: PrefsManager? = null

        /**
         * Get instance for [PrefsManager] or create new if not found.
         */
        fun getInstance(application: Application): PrefsManager {
            return when {
                INSTANCE != null -> INSTANCE!!
                else -> {
                    INSTANCE = PrefsManager(application)
                    INSTANCE!!
                }
            }
        }
    }

    /**
     * [android.content.SharedPreferences] instance to make read and write operations.
     */
    private val mSharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * [Gson] instance to parse json into pojo and vice-versa.
     */
    private val gson by lazy { Gson() }

    /**
     * Returns cache albums.
     *
     * @return List of [ModelAlbum] saved into cache.
     */
    fun getAlbumFromCache(): List<ModelAlbum> {
        return mSharedPreferences.getString(KEY_ALBUM_CACHE, null)?.let { jsonString ->
            gson.fromJson(jsonString, Array<ModelAlbum>::class.java).toList()
        } ?: emptyList()
    }

    /**
     * Saves albums into cache.
     *
     * @param albums List of [ModelAlbum] to be saved into cache.
     */
    fun saveAlbumsIntoCache(albums: List<ModelAlbum>) {
        mSharedPreferences.edit().putString(KEY_ALBUM_CACHE, gson.toJson(albums)).apply()
    }
}