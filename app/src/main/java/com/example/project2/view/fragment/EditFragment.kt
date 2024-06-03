package com.example.project2.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project2.R
import com.example.project2.databinding.FragmentEditBinding
import com.example.project2.databinding.FragmentViewtaskBinding
import com.example.project2.model.TodoTask
import com.example.project2.viewmodel.TodoTaskViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val app: TodoTaskViewModel by viewModels()
    private lateinit var receivedTask: TodoTask

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Funciones necesarias
        setToolbar()
        loadTask()
    }

    private fun setToolbar (){
        val toolbar : Toolbar = binding.contentToolbar.toolbarEdit
        toolbar.setNavigationOnClickListener {
            // Navegar a otro fragmento cuando se hace clic en el icono de navegación
            findNavController().popBackStack()
        }
    }

    private fun loadTask() {
        val receivedBundle = arguments
        receivedTask = receivedBundle?.getSerializable("dataTask") as TodoTask
        binding.editTextName.setText(receivedTask.name)
        binding.editTextDescription.setText(receivedTask.description)
//        binding.spinner_categoria.setText(receivedTask.category)
//        binding.spinner_categoria.setText(receivedTask.priority)
    }

}