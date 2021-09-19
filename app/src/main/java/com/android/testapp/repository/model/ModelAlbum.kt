package com.android.testapp.repository.model

/**
 * Data class for album.
 *
 * @param userId
 * @param id
 * @param title Title of the album.
 */
data class ModelAlbum(
    val userId: Int,
    val id: Int,
    val title: String
)