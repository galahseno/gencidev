package com.seno.core.presentation.utils

import com.seno.core.domain.DataError
import com.seno.core.presentation.R

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.error_request_timeout
        )

        DataError.Network.TOO_MANY_REQUEST -> UiText.StringResource(
            R.string.error_too_many_request
        )

        DataError.Network.UNAUTHORIZED -> UiText.StringResource(
            R.string.invalid_login_credentials
        )

        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.error_no_internet
        )

        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.error_payload_too_large
        )

        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.error_server_error
        )

        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )

        DataError.Local.NO_DATA -> UiText.StringResource(
            R.string.error_no_data
        )

        else -> UiText.StringResource(
            R.string.error_unknown
        )
    }
}