package com.example.project2.repository

import android.util.Log
import com.example.project2.model.TodoTask
import com.example.project2.utils.TaskListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoTaskRepository () {
    private val firestore = FirebaseFirestore.getInstance()
    private fun getCurrentUserEmail(): String {

        //return firebaseAuth.currentUser?.email.toString()
        return "paul@mail.com"
    }


    suspend fun getTodoTasks(listener: TaskListener) {
        withContext(Dispatchers.IO) {
            val db = FirebaseFirestore.getInstance()
            val currentUserEmail = getCurrentUserEmail()
            val tasks = mutableListOf<TodoTask>()

            db.collection("Users").document(currentUserEmail).collection("Tasks")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        for (doc in querySnapshot.documents) {
                            val task = TodoTask(
                                doc.id,
                                doc.getString("name") ?: "task",
                                doc.getString("description") ?: "",
                                doc.getString("category") ?: "general",
                                doc.getString("priority") ?: "Baja",
                                doc.getString("imagePath") ?: ""
                            )
                            tasks.add(task)
                        }
                        listener.onTasksLoaded(tasks)
                        Log.d("TodoTaskViewModel", "Tareas obtenidas: ${tasks.toString()}")
                    } else {
                        Log.d("TodoTaskViewModel", "No tasks found")
                    }
                }
                .addOnFailureListener { exception ->
                    listener.onTasksLoadError(exception)
                    Log.e("TodoTaskViewModel", "Error getting tasks", exception)
                }
        }
    }


    suspend fun insertTodoTasks(task: TodoTask) {
        withContext(Dispatchers.IO) {
            val name = task.name
            val description = task.description
            val category = task.category
            val priority = task.priority
            val imagePath = task.imagePath


            firestore.collection("Users").document(getCurrentUserEmail()).
            collection("Tasks").add(
                hashMapOf(
                    "name" to name,
                    "description" to description,
                    "category" to category,
                    "priority" to priority,
                    "imagePath" to imagePath
                )
            ).addOnSuccessListener{
                Log.d("TaskApp", "Task inserted successfully")
            }.addOnFailureListener {
                Log.d("TaskApp", "Task inserted successfully")
            }
        }
    }

    suspend fun deleteTodoTasks(task: TodoTask) = withContext(Dispatchers.IO) {
        val taskId = task.id

        val docRef = firestore.collection("Users").document(getCurrentUserEmail()).
        collection("Tasks").document(taskId)

        docRef.delete()
    }

    suspend fun updateTodoTasks(task: TodoTask) = withContext(Dispatchers.IO) {
        val taskId = task.id
        val name = task.name
        val description = task.description
        val category = task.category
        val priority = task.priority
        val imagePath = task.imagePath

        val docRef = firestore.collection("Users").document(getCurrentUserEmail()).
        collection("Tasks").document(taskId)

        docRef.set(hashMapOf(
            "name" to name,
            "description" to description,
            "category" to category,
            "priority" to priority,
            "imagePath" to imagePath
        ))
    }


}