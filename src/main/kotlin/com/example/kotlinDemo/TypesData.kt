package com.example.kotlinDemo

data class NewCourseInput(
    val courseId: Int,
    val courseCode: String,
    val courseName: String,
    val courseDescription: String,
    val topic: NewTopicInput? = null  // Embedded NewTopicInput to handle topic creation alongside the course
)

data class NewTopicInput(
    val topicId: Int,
    val topicCode: String,
    val topicName: String
)

data class UpdateCourseInput(
    val courseId: Int,
    val courseCode: String? = null,
    val courseName: String? = null,
    val courseDescription: String? = null,
    val topicIds: List<Int>? = null  // Adjusted to nullable to allow partial updates
)

data class UpdateTopicInput(
    val topicId: Int,
    val topicCode: String? = null,
    val topicName: String? = null
)
