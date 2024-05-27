package com.project.group.rupp.dse.classtracking.models

data class GetMember (
    val member_id: String,
    val name: String,
    val role: String,
    val account_id: String,
    val join_date: String
)
