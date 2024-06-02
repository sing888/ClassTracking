package com.project.group.rupp.dse.classtracking.models

data class PostTeacherAttendance(
    val active: Boolean,
    val date: String,
    val member_id: String,
    val classroom_id: String
)