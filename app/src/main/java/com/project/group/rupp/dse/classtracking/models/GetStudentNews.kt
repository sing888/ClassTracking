package com.project.group.rupp.dse.classtracking.models

import java.util.Date

data class GetStudentNews (
    val news_id: String,
    val deadline: Date,
    val title: String,
    val body: String,
    val post_date: Date
)