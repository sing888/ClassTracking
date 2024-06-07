package com.project.group.rupp.dse.classtracking.models

data class GetNews (
    val deadline: String,
    val title: String,
    val body: String,
    val post_date: String,
    val classroomId: String
)