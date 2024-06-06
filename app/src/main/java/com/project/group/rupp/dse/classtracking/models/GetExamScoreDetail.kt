package com.project.group.rupp.dse.classtracking.models

data class GetExamScoreDetail (
    val member_id: String,
    val name: String,
    val score: String,
    val max_score: String
)