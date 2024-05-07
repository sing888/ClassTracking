package com.project.group.rupp.dse.classtracking.models

data class GetCreateRoom (
    val classroom_id: String,
    val room_code: String,
    val name: String,
    val status: String,
    val create_date: String,
    val password: String,
)