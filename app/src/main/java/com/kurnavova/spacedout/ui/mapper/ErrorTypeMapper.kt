package com.kurnavova.spacedout.ui.mapper

import androidx.annotation.StringRes
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.usecase.model.UseCaseError

/**
 * Maps an [ApiResult.Error] to a string resource ID.
 */
@StringRes
fun UseCaseError.toErrorMessage(): Int = when(this) {
    UseCaseError.Server -> R.string.error_server
    UseCaseError.Client -> R.string.error_client
    UseCaseError.Network -> R.string.error_network
    UseCaseError.Unknown -> R.string.error_unknown
}
