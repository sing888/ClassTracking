package com.project.group.rupp.dse.classtracking.models

data class Response<T> (
    val status: String? = null,
    val message: String? = null,
    val data: T? = null
)
