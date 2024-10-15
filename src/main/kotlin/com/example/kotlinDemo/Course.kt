package com.example.kotlinDemo

data class Course(
    val id: Int,
    var code: String,
    var name: String,
    var description: String,
    var topicIds: List<Int>  // Renamed for clarity, aligning with plural naming conventions
) {
    companion object {
        val courses = mutableListOf(
            Course(
                id = 1,
                code = "CCS",
                name = "Computer Science",
                description = "Computer science is the study of computers, both hardware and software.",
                topicIds = listOf(1)
            ),
            Course(
                id = 2,
                code = "TMC",
                name = "Mathematics and Computer Science",
                description = "Study of computers in relation to mathematical algorithms.",
                topicIds = listOf(1, 2)
            ),
            Course(
                id = 3,
                code = "Med",
                name = "Medicine and Surgery",
                description = "Fields focused on diagnosing, treating, and preventing diseases.",
                topicIds = listOf(3)
            )
        )

        fun getCourseById(id: Int?): Course? {
            return courses.find { it.id == id }  // Simplified using Kotlin's `find`
        }
    }
}
