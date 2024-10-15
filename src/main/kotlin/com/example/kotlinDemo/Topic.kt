package com.example.kotlinDemo

data class Topic(
    val id: Int,
    var code: String,
    var name: String
) {
    companion object {
        val topics = mutableListOf(
            Topic(
                id = 1,
                code = "CCS 101",
                name = "Discrete Structures"
            ),
            Topic(
                id = 2,
                code = "TMC 103",
                name = "Linear Algebra"
            ),
            Topic(
                id = 3,
                code = "MED 105",
                name = "Anatomy"
            )
        )

        fun getTopicById(id: Int): Topic? {
            return topics.find { it.id == id }  // Simplified using Kotlin's `find`
        }
    }
}
