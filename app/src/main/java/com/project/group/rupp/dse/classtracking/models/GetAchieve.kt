package com.project.group.rupp.dse.classtracking.models

data class GetAchieve (
    val classroom_id: String,
    val room_code: String,
    val name: String,
    val status: String,
    val password: String?,
)