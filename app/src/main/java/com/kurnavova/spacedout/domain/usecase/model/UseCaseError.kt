package com.kurnavova.spacedout.domain.usecase.model

/**
 * Represents the type of error that occurred.
 */
enum class UseCaseError {
    Server,
    Client,
    Network,
    Unknown,
}
