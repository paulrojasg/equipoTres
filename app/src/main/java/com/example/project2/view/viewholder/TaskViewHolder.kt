package com.example.project2.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.project2.R
import com.example.project2.databinding.CardTaskBinding
import com.example.project2.model.TodoTask

class TaskViewHolder(binding: CardTaskBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingTask = binding
    val navController = navController
    fun setCardTask(task: TodoTask) {
        bindingTask.taskName.text = task.name
        bindingTask.taskPriority.text = "Prioridad: ${task.priority}"
        // Cargar la imagen desde la URL y aplicar la transformación CircleCrop
//        Glide.with(bindingTask.root.context)
//            .load(task.imagePath)
//            .transform(CircleCrop())
//            .into(bindingTask.taskImage)

        bindingTask.editButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", task)
            navController.navigate(R.id.action_fragment_viewtask_to_editTaskFragment, bundle)
        }
    }
}