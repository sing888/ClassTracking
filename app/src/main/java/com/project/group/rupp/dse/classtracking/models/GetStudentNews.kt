package com.project.group.rupp.dse.classtracking.models

import java.util.Date

data class GetStudentNews (
    val news_id: String,
    val deadline: String,
    val title: String,
    val body: String,
    val post_date: String
)