package com.project.group.rupp.dse.classtracking.models

data class GetStudentAttendanceDetail (
    val attendance_id: String,
    val date: String,
    val active: Boolean
)