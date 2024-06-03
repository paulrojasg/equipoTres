package com.example.project2.viewmodel

import com.example.project2.utils.TaskListener
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.example.project2.model.TodoTask
import com.example.project2.repository.TodoTaskRepository
import kotlinx.coroutines.launch

class TodoTaskViewModel (application: Application) : AndroidViewModel(application)  {

    private val todoTaskRepository = TodoTaskRepository()

    private val _listTodoTask = MutableLiveData<MutableList<TodoTask>>()
    val listTodoTask: LiveData<MutableList<TodoTask>> get() = _listTodoTask

    private val _progressState = MutableLiveData(false)
    val progressState: LiveData<Boolean> = _progressState

    init {
        getTodoTasks()
    }

    fun getTodoTasks() {
        viewModelScope.launch {
            todoTaskRepository.getTodoTasks(object : TaskListener {
                override fun onTasksLoaded(tasks: MutableList<TodoTask>) {
                    _listTodoTask.value = tasks
                }

                override fun onTasksLoadError(exception: Exception) {
                    Log.e("error: ", exception.toString())
                }
            })
        }
    }

    fun insertTodoTasks(task: TodoTask) {
        viewModelScope.launch {
            try {
                todoTaskRepository.insertTodoTasks(task)
            } catch (e: Exception) {
                Log.d("error: ", e.toString())
            }
        }
    }
    fun deleteTodoTasks(task: TodoTask) {
        viewModelScope.launch {
            try {
                todoTaskRepository.deleteTodoTasks(task)
            } catch (e: Exception) {
                Log.d("error: ", e.toString())
            }
        }
    }

    fun updateTodoTasks(task: TodoTask) {
        viewModelScope.launch {
            try {
                todoTaskRepository.updateTodoTasks(task)
            } catch (e: Exception) {
                Log.d("error: ", e.toString())
            }
        }
    }


}



