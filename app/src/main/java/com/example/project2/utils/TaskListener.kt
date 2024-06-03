package com.example.project2.utils

import com.example.project2.model.TodoTask

interface TaskListener {
    fun onTasksLoaded(tasks: MutableList<TodoTask>)
    fun onTasksLoadError(exception: Exception)
}