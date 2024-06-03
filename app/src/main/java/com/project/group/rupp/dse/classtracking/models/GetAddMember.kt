package com.project.group.rupp.dse.classtracking.models

data class GetAddMember(
    val member_id: String,
    val name: String,
    val role: String,
    val join_date: String
)