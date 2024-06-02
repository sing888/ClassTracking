package com.project.group.rupp.dse.classtracking.models

data class GetAttendanceDetailAll (
    val name: String,
    val percentage: Float,
    val present: Int,
    val total: Int
)
