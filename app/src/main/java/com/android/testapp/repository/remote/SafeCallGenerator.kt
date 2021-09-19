package com.android.testapp.repository.remote

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Class with functions to create generic server calls.
 */
object SafeCallGenerator {

    /**
     * Suspend function to be called from a coroutine. Calls an api and returns the response.
     *
     * @param dispatcher [CoroutineDispatcher] for the processing thread.
     * @param apiCall Suspend function declared in [API] interface. Corresponds to the api call.
     *
     * @return [ResultWrapper] object as server response.
     */
    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                //Make server call
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                when (throwable) {
                    //If network unavailable
                    is IOException -> ResultWrapper.NetworkError
                    //If server error
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse =
                            convertErrorBody(
                                throwable
                            )
                        ResultWrapper.GenericError(code, errorResponse)
                    }
                    //Success
                    else -> {
                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }

    /**
     * Parses [HttpException] into [ErrorResponse].
     *
     * @param throwable [HttpException] instance.
     *
     * @return [ErrorResponse] object. Returns null if empty message in the exception or if any
     * error occurs during parsing.
     */
    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.toString()?.let {
                Gson().fromJson(it, ErrorResponse::class.java)!!
            }
        } catch (exception: Exception) {
            null
        }
    }
}