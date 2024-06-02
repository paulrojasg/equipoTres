package com.example.project2.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.project2.model.TodoTask
import com.example.project2.databinding.CardTaskBinding
import com.example.project2.view.viewholder.TaskViewHolder
class TaskAdapter(private val listTask:MutableList<TodoTask>, private val navController: NavController):RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = CardTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, navController)
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val appointment = listTask[position]
        holder.setCardAppointment(appointment)
    }
}