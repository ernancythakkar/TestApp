package com.android.testapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.testapp.repository.AlbumRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class AlbumsViewModelTest {


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = mock(AlbumRepository::class.java)

    @Test
    fun testNull() {

       
    }

    @Test
    fun doNotFetchWithoutObservers() {


    }

    @Test
    fun doNotFetchWithoutObserverOnConnectionChange() {


    }

}
