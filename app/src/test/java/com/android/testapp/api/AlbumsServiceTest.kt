package com.android.testapp.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.testapp.repository.remote.API
import com.android.testapp.repository.remote.Config
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class AlbumsServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var service: API
    private lateinit var mockWebServer: MockWebServer


    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }



    @Test
    fun requestAlbums() {
        runBlocking {
            enqueueResponse("albums.json")
            val resultResponse = service.getAlbums().body()
            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/albums"))
        }
    }

    @Test
    fun getNewsResponse() {
        runBlocking {
            enqueueResponse("albums.json")
            val resultResponse = service.getAlbums().body()
            val albumList = resultResponse
            assertThat(resultResponse?.size, `is`(100))
            assertThat(albumList?.size, `is`(100))
        }
    }


    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
            ?.getResourceAsStream("api-response/$fileName")
        val source = inputStream?.let { it.source().buffer() }
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        source?.readString(Charsets.UTF_8)?.let {
            mockResponse.setBody(
                it
            )
        }?.let {
            mockWebServer.enqueue(
                it
            )
        }
    }
}


