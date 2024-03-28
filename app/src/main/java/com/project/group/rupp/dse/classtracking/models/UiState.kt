package com.project.group.rupp.dse.classtracking.models

data class UiState<T>(
    val status: UiStateStatus,
    val message: String? = null,
    val data: T? = null
)

enum class UiStateStatus {
    loading, success, error
}