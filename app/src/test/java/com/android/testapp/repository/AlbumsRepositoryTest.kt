package com.android.testapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.testapp.repository.remote.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*


@RunWith(JUnit4::class)
class AlbumsRepositoryTest {

    private lateinit var repository: AlbumRepository
    private val service = mock(API::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {

    }

    @Test
    fun loadNewsFromNetwork() {
        runBlocking {

        }
    }
}
