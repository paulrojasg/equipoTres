package com.example.project2.data

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.project2.model.TodoTask
import com.example.project2.view.adapter.TaskAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class TodoTaskDao() {
    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    private fun getCurrentUserEmail(): String {

        //return firebaseAuth.currentUser?.email.toString()
        return "paul@mail.com"
    }

    interface TasksListener {
        fun onTasksLoaded(tasks: MutableList<TodoTask>)
        fun onTasksLoadError(exception: Exception)
    }

    fun getTodoTasks(listener: TasksListener) {
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


    fun insertTodoTask(task: TodoTask){

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


    fun deleteTodoTask(task: TodoTask){

        val taskId = task.id

        val docRef = firestore.collection("Users").document(getCurrentUserEmail()).
        collection("Tasks").document(taskId)

        docRef.delete()

    }

    fun updateTodoTask(task: TodoTask){

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


