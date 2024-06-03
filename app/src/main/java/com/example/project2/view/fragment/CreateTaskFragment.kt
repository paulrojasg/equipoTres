package com.example.project2.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.project2.R
import com.example.project2.viewmodel.TodoTaskViewModel
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project2.databinding.FragmentCreateTaskBinding
import com.example.project2.model.TodoTask
import com.example.project2.retrofit.Flower
import com.example.project2.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateTaskFragment : Fragment() {

    private var _binding: FragmentCreateTaskBinding? = null
    private val binding get() = _binding!!

    private val app: TodoTaskViewModel by viewModels()
    private  var categoryOption = "Categoría"
    private  var priorityOption = "Prioridad"
    private val categoryOptions = listOf("General", "Familia", "Compras", "Estudio", "Trabajo", "Mascotas")
    private val priorityOptions = listOf("Baja", "Media", "Alta")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        setupSpinners()
        prepareListeners()

        return view
    }

    private fun setupSpinners() {
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryOptions)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategory.adapter = categoryAdapter

        val priorityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityOptions)
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPriority.adapter = priorityAdapter

        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val category = categoryOptions[position]
                categoryOption = category
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val priority = priorityOptions[position]
                priorityOption = priority
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }


    private fun prepareListeners() {


        val toolbar : Toolbar = binding.contentToolbar.toolbarEdit
        toolbar.setNavigationOnClickListener {
            app.getTodoTasks()
            findNavController().popBackStack()
        }

        // Save button
        binding.btnSave.setOnClickListener {
            val name = binding.etvName.editableText.toString()
            val description = binding.etvDescription.editableText.toString()
            val category = categoryOption
            val priority = priorityOption

            if (name.isEmpty()) {
                Toast.makeText(context, "Nombre inválido", Toast.LENGTH_SHORT).show()
            } else {
                val retrofitBring = RetrofitClient.consumeApi.getBring()
                retrofitBring.enqueue(object : Callback<Flower> {
                    override fun onResponse(call: Call<Flower>, response: Response<Flower>) {
                        var imagePath = response.body()?.file ?: ""

                        val startingIndex = imagePath.indexOf("/cache/")
                        imagePath = if (startingIndex != -1) {
                            imagePath.substring(startingIndex)
                        } else {
                            ""
                        }

                        val task = TodoTask(
                            id="temporal_id",
                            name=name,
                            description = description,
                            category = category,
                            priority = priority,
                            imagePath = imagePath
                        )

                        app.insertTodoTasks(task)
                        app.getTodoTasks()
                        Log.d("TaskApp", "Task created")
                        Toast.makeText(context, "Tarea creada exitosamente", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_createTaskFragment_to_viewTaskFragment)


                    }

                    override fun onFailure(call: Call<Flower>, t: Throwable) {
                        Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                    }
                })

            }


        }


    }

}