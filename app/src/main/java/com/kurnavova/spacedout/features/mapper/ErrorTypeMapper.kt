package com.kurnavova.spacedout.features.mapper

import androidx.annotation.StringRes
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.model.ErrorType

/**
 * Maps an [ApiResult.Error] to a string resource ID.
 */
@StringRes
fun ApiResult.Error.toErrorMessage(): Int = when(type) {
    ErrorType.Server -> R.string.error_server
    ErrorType.Client -> R.string.error_client
    ErrorType.Network -> R.string.error_network
    ErrorType.Unknown -> R.string.error_unknown
}
