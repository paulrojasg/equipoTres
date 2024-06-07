package com.example.project2.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.project2.databinding.FragmentDetailBinding
import com.example.project2.model.TodoTask
import com.example.project2.viewmodel.TodoTaskViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val app: TodoTaskViewModel by viewModels()
    private lateinit var receivedTask: TodoTask

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using View Binding
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catchIncomingData()
    }

    private fun catchIncomingData() {
        val receivedBundle = arguments
        receivedTask = receivedBundle?.getSerializable("clave") as TodoTask


        Log.d("DetailFragment", "Image URL: ${receivedTask.imagePath}") // Verifica la URL en los logs

        // Cargar la imagen desde la URL en el ImageView
        Glide.with(this)
            .load(receivedTask.imagePath)
            .into(binding.flowerImage)

        // Ejemplos de cómo editar las variables
        binding.tvTaskName.text = receivedTask.name
        binding.tvTaskDescription.text = receivedTask.description
    }
}
