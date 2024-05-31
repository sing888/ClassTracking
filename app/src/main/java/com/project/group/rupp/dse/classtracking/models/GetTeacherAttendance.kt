package com.project.group.rupp.dse.classtracking.models

data class GetTeacherAttendance(
    val member_id: String,
    val name: String,
    val attendance: String?
)