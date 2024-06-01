package com.example.project2.model

import java.io.Serializable

data class TodoTask(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val priority: String
): Serializable