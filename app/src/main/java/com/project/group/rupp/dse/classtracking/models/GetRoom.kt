package com.project.group.rupp.dse.classtracking.models

data class GetRoom (
    val classroom_id: String,
    val room_code: String,
    val name: String,
    val password: String?,
)