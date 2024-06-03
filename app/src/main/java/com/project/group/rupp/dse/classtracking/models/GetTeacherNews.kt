package com.project.group.rupp.dse.classtracking.models

import android.widget.DatePicker

data class GetTeacherNews (
    val news_id: String,
    val deadline: String,
    val title: String,
    val body: String,
    val post_date: String
)