package com.project.group.rupp.dse.classtracking.models

data class GetStudentScoreDetail (
    val average_score: Double,
    val subject_score: List<SubjectScore>,
    val score: List<Score>

)