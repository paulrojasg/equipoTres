package com.example.project2.repository

import com.example.project2.data.TodoTaskDao
import com.example.project2.model.TodoTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoTaskRepository () {
    private val todoTaskDao = TodoTaskDao()

    fun getTodoTasks(listener: TodoTaskDao.TasksListener) {
        todoTaskDao.getTodoTasks(listener)
    }

//    suspend fun getTodoTasks(): MutableList<TodoTask> {
//        return withContext(Dispatchers.IO) {
//            todoTaskDao.getTodoTasks()
//        }
//    }

    suspend fun insertTodoTasks(task: TodoTask) {
        withContext(Dispatchers.IO) {
            todoTaskDao.insertTodoTask(task)
        }
    }

    suspend fun deleteTodoTasks(task: TodoTask) = withContext(Dispatchers.IO) {
        todoTaskDao.deleteTodoTask(task)
    }

    suspend fun updateTodoTasks(task: TodoTask) = withContext(Dispatchers.IO) {
        todoTaskDao.updateTodoTask(task)
    }


}