package com.android.testapp.repository.remote

/**
 * Sealed class to save different states of server response. [T] is the data type of server
 * response on success.
 */
sealed class ResultWrapper<out T> {
    /**
     * Data class to store successful server response.
     * @param value [T] object for storing the server response.
     *
     * @return [ResultWrapper] object for [Success].
     */
    data class Success<out T>(val value: T) : ResultWrapper<T>()

    /**
     * Data class to store server error.
     * @param code Server error code.
     * @param error [ErrorResponse] object for storing the server error.
     *
     * @return [ResultWrapper] object for [GenericError].
     */
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) :
        ResultWrapper<Nothing>()

    /**
     * Object to save network error.
     */
    object NetworkError : ResultWrapper<Nothing>()
}