package com.example.kotlinDemo

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class CourseController {

    @QueryMapping
    fun courses(): List<Course> = Course.courses

    @QueryMapping
    fun courseById(@Argument id: Int?): Course? = Course.getCourseById(id)

    @SchemaMapping
    fun topics(course: Course): List<Topic?> =
        course.topicIds.mapNotNull(Topic::getTopicById)

    @MutationMapping
    fun addCourse(@Argument input: NewCourseInput): Course {
        val newTopic = input.topic?.let { addOrUpdateTopic(it.toTopic()) }
            ?: Topic(input.courseId, "", "Default Topic")

        return Course(
            id = input.courseId,
            code = input.courseCode,
            name = input.courseName,
            description = input.courseDescription,
            topicIds = listOf(newTopic.id)
        ).also { Course.courses += it }
    }

    @MutationMapping
    fun updateCourse(@Argument input: UpdateCourseInput): Course? =
        Course.courses.find { it.id == input.courseId }?.apply {
            input.update(this)
        }

    @MutationMapping
    fun updateTopic(@Argument input: UpdateTopicInput): Topic? =
        Topic.topics.find { it.id == input.topicId }?.apply {
            input.update(this)
        }

    @MutationMapping
    fun deleteCourse(@Argument courseId: Int): Boolean =
        Course.courses.removeIf { it.id == courseId }

    @MutationMapping
    fun deleteTopic(@Argument topicId: Int): Boolean {
        val isDeleted = Topic.topics.removeIf { it.id == topicId }
        if (isDeleted) {
            Course.courses.forEach { course ->
                course.topicIds = course.topicIds.filterNot { it == topicId }
            }
        }
        return isDeleted
    }

    @MutationMapping
    fun addTopic(@Argument input: NewTopicInput): Topic =
        addOrUpdateTopic(input.toTopic())

    private fun addOrUpdateTopic(topic: Topic): Topic {
        Topic.topics.removeIf { it.id == topic.id }
        Topic.topics += topic
        return topic
    }
}

// Extension function to convert input to Topic
private fun NewTopicInput.toTopic() = Topic(
    id = this.topicId,
    code = this.topicCode,
    name = this.topicName
)

// UpdateCourseInput Extension for updating course
private fun UpdateCourseInput.update(course: Course) {
    course.code = this.courseCode ?: course.code
    course.name = this.courseName ?: course.name
    course.description = this.courseDescription ?: course.description
    course.topicIds = this.topicIds ?: course.topicIds
}

// UpdateTopicInput Extension for updating topic
private fun UpdateTopicInput.update(topic: Topic) {
    topic.code = this.topicCode ?: topic.code
    topic.name = this.topicName ?: topic.name
}
