package com.example.project2.view.fragment

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.project2.databinding.FragmentDetailBinding
import com.example.project2.model.TodoTask

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var receivedTask: TodoTask

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        setToolbar()
        catchIncomingData()
    }

    private fun controladores() {
        // Lógica para la flecha de atrás
    }

    private fun setToolbar() {
        val toolbar: Toolbar = binding.contentToolbar.toolbarDetail
        toolbar.setNavigationOnClickListener {
            // Navegar a otro fragmento cuando se hace clic en el icono de navegación
            findNavController().popBackStack()
        }
    }

    private fun catchIncomingData() {
        val receivedBundle = arguments
        receivedTask = receivedBundle?.getSerializable("clave") as TodoTask

        Log.d("DetailFragment", "Image URL: ${receivedTask.imagePath}") // Verifica la URL en los logs

        // Cargar la imagen desde la URL en el ImageView
        Glide.with(this)
            .load("https://loremflickr.com/${receivedTask.imagePath}")
            .transform(CircleCrop())
            .into(binding.flowerImage)

        // Ejemplos de cómo editar las variables
        binding.tvTaskName.text = receivedTask.name

        // Convierte la descripción a Editable antes de establecer el texto
        val editableDescription: Editable = Editable.Factory.getInstance().newEditable(receivedTask.description)
        binding.tvTaskDescription.text = editableDescription

        binding.tvTaksPriority.text = receivedTask.priority
        binding.TvTaksCategory.text = receivedTask.category
    }
}
