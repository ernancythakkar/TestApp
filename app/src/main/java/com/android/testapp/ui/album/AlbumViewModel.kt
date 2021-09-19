package com.android.testapp.ui.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.testapp.repository.AlbumRepository
import com.android.testapp.repository.model.ModelAlbum

/**
 * ViewModel for [AlbumFragment].
 */
class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Instance for [AlbumRepository] to fetch albums.
     */
    private val albumRepository by lazy {
        AlbumRepository.getInstance(application)
    }

    /**
     * LiveData variables to save data and view states.
     */
    private val albums = MutableLiveData<List<ModelAlbum>>()
    private val errorMessage = MutableLiveData<String>()
    private val showLoader = MutableLiveData(false)

    /**
     * Fetches the albums list from [AlbumRepository].
     */
    private fun fetchAlbums() {
        //Reset the error and show loading
        errorMessage.value = ""
        showLoader.value = true
        albumRepository.getAlbums(
            {
                //Hide loading and update album data
                showLoader.value = false
                albums.value = it
            }, {
                //Hide loading and show error
                showLoader.value = false
                errorMessage.value = it
            })
    }

    /**
     * Called by view when view is initialized.
     */
    fun initialize() {
        fetchAlbums()
    }

    /**
     * Observables for live data which can be used by view.
     */
    fun albumObservable(): LiveData<List<ModelAlbum>> = albums
    fun errorMessageObservable(): LiveData<String> = errorMessage
    fun showLoaderObservable(): LiveData<Boolean> = showLoader

}