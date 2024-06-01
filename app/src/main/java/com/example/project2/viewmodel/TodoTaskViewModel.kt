package com.example.project2.viewmodel

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

    fun getTodoTasks() {
        viewModelScope.launch {
            try {
                _listTodoTask.value = todoTaskRepository.getTodoTasks()
            } catch (e: Exception) {
                Log.d("error: ", e.toString())
            }
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

