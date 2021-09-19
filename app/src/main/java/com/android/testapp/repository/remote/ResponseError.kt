package com.android.testapp.repository.remote

/**
 * Wrapper class to save retrofit error for api calls.
 *
 * @param status Status code of api request.
 * @param errors List of errors returned from server.
 */
data class ErrorResponse(
    val status: Int,
    val errors: List<ErrorBody>?
) {
    /**
     * Returns error message as user readable string.
     */
    fun getErrorMessage() =
        if (errors?.isNotEmpty() == true) errors[0].message
        else null
}

/**
 * Data class to save error message.
 *
 * @param message User readable string for error.
 */
data class ErrorBody(
    val message: String?
)