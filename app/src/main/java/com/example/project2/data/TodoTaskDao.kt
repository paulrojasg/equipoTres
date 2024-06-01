package com.example.project2.data

import android.util.Log
import com.example.project2.model.TodoTask
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

val TAG = "mycustomtag"



class TodoTaskDao() {
    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val TAG = "TodoTaskDao"

    private fun getCurrentUserEmail(): String {

        //return firebaseAuth.currentUser?.email.toString()
        return "paul@mail.com"
    }

    fun getTodoTasks(): MutableList<TodoTask> {
        val taskList = mutableListOf<TodoTask>()
        val currentUserEmail = getCurrentUserEmail()

        firestore.collection("Users").document(currentUserEmail).collection("Tasks")
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    for (doc in querySnapshot.documents) {
                        val task = TodoTask(
                            doc.id,
                            doc.getString("name") ?: "task",
                            doc.getString("description") ?: "",
                            doc.getString("category") ?: "general",
                            doc.getString("priority") ?: "1",
                        )
                        taskList.add(task)
                    }
                } else {
                    Log.d(TAG, "No tasks found")
                }
            }
            .addOnFailureListener { exception ->

                Log.d(TAG, "Error getting tasks", exception)

            }
        return taskList

    }

    fun insertTodoTask(task: TodoTask){

        val name = task.name
        val description = task.description
        val category = task.category
        val priority = task.priority


        firestore.collection("Users").document(getCurrentUserEmail()).
        collection("Tasks").add(
            hashMapOf(
                "name" to name,
                "description" to description,
                "category" to category,
                "priority" to priority
            )
        ).addOnFailureListener {
            Log.d("Error", it.cause.toString())
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

        val docRef = firestore.collection("Users").document(getCurrentUserEmail()).
        collection("Tasks").document(taskId)

        docRef.set(hashMapOf(
            "name" to name,
            "description" to description,
            "category" to category,
            "priority" to priority
        ))

    }


}


