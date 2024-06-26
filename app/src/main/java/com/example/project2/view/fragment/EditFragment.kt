package com.example.project2.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project2.R
import com.example.project2.databinding.FragmentEditBinding
import com.example.project2.model.TodoTask
import com.example.project2.viewmodel.TodoTaskViewModel
import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import android.graphics.Color
import android.widget.Toast
import com.example.project2.retrofit.Category
import com.example.project2.retrofit.Flower
import com.example.project2.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val app: TodoTaskViewModel by viewModels()
    private lateinit var receivedTask: TodoTask

    private  var categoryOption = "Categoría"
    private  var priorityOption = "Prioridad"
    var categoriesOptions = listOf<String>("")
    private val priorityOptions = listOf("Prioridad", "Baja", "Media", "Alta")
    private var imagePath = ""

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
        setupCategories()
        setToolbar()
        controladores()
    }

    // ===========================   controladores implemention   ===========================
    private fun controladores() {
        binding.deleteButton.setOnClickListener {
            try {
                showDeleteConfirmationDialog()
            } catch(e: Exception) {
                Log.e("Error","Navegación fallida",e)
            }
        }
        binding.editButton.setOnClickListener {
            try {
                validateData()
            } catch(e: Exception) {
                Log.e("Error","Navegación fallida",e)
            }
        }
    }

    private fun setupCategories() {
        val retrofitBring = RetrofitClient.mockableConsumeApi.getBring()
        retrofitBring.enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                val categoriesListFor = mutableListOf<String>()
                for (c in response.body() ?: listOf(Category(id=0, name=""))) {
                    categoriesListFor.add(c.name)
                }
                categoriesOptions = categoriesListFor
                setupSpinners()
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    // ===========================   backward implementation   ===========================
    private fun setToolbar (){
        val toolbar : Toolbar = binding.contentToolbar.toolbarEdit
        toolbar.setNavigationOnClickListener {
            // Navegar a otro fragmento cuando se hace clic en el icono de navegación
            findNavController().popBackStack()
        }
    }
    // ===========================   Load information in the fields   ===========================
    private fun loadTask() {
        val receivedBundle = arguments

        // Extrae el TodoTask del Bundle
        receivedTask = receivedBundle?.getSerializable("clave") as TodoTask

        // Accede a las propiedades del TodoTask
        val id = receivedTask.id
        val name = receivedTask.name
        val description = receivedTask.description
        val category = receivedTask.category
        val priority = receivedTask.priority
        imagePath = receivedTask.imagePath

        // Establece los valores de los editText
        binding.editTextName.setText(name)
        binding.editTextDescription.setText(description)

        // Establece los valores seleccionados en los Spinners
        val categoryPosition = categoriesOptions.indexOf(category)
        if (categoryPosition >= 0) {
            binding.spinnerCategoria.setSelection(categoryPosition)
        }

        val priorityPosition = priorityOptions.indexOf(priority)
        if (priorityPosition >= 0) {
            binding.spinnerPrioridad.setSelection(priorityPosition)
        }
    }

    // ===========================   Spinner personalization   ===========================
    private fun setupSpinners() {
        val categoryAdapter = CustomArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriesOptions)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategoria.adapter = categoryAdapter

        val priorityAdapter = CustomArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityOptions)
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPrioridad.adapter = priorityAdapter

        binding.spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val category = categoriesOptions[position]
                categoryOption = category
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spinnerPrioridad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val priority = priorityOptions[position]
                priorityOption = priority
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        loadTask()
    }

    class CustomArrayAdapter(context: Context, resource: Int, objects: List<String>) :
        ArrayAdapter<String>(context, resource, objects) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.setTextColor(Color.WHITE)
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.setTextColor(Color.WHITE)
            textView.setBackgroundResource(R.color.rose_strong)
            textView.text = getItem(position)
            return view
        }
    }

    // ===========================   Button edit validation and implemention   ===========================
    private fun validateData() {
        val listEditText = listOf(binding.editTextName, binding.editTextDescription)
//        val spinners = listOf(binding.spinnerCategoria, binding.spinnerPrioridad)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No necesitamos hacer nada aquí
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No necesitamos hacer nada aquí
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        for (editText in listEditText) {
            editText.addTextChangedListener(textWatcher)
        }

        val isNameFilled = binding.editTextName.text?.isNotEmpty() ?: false
        val isCategoryValid = binding.spinnerCategoria.selectedItem != "Categoría"
        val isPriorityValid = binding.spinnerPrioridad.selectedItem != "Prioridad"

        if (!isNameFilled) {
            showAlert("Error", "Debe llenar el nombre de la tarea")
        } else if (!isCategoryValid || !isPriorityValid) {
            showAlert("Error", "Debe seleccionar una categoría y prioridad válidas")
        } else {
            updateTask()
        }
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun updateTask() {
        // Primero, recoge los datos actualizados de los campos de entrada
        val updatedName = binding.editTextName.text.toString()
        val updatedDescription = binding.editTextDescription.text.toString()
        val updatedCategory = binding.spinnerCategoria.selectedItem.toString()
        val updatedPriority = binding.spinnerPrioridad.selectedItem.toString()

        // Comprueba si la categoría ha cambiado
        if (updatedCategory != receivedTask.category) {
            // Si la categoría ha cambiado, haz la petición para obtener la imagen
            val retrofitBring = RetrofitClient.flowersConsumeApi.getBring()
            retrofitBring.enqueue(object : Callback<Flower> {
                override fun onResponse(call: Call<Flower>, response: Response<Flower>) {
                    var imagePath = response.body()?.file ?: ""

                    val startingIndex = imagePath.indexOf("/cache/")
                    imagePath = if (startingIndex != -1) {
                        imagePath.substring(startingIndex)
                    } else {
                        ""
                    }
                    updateTaskSecondary(updatedName, updatedDescription, updatedCategory, updatedPriority, imagePath)
                }

                override fun onFailure(call: Call<Flower>, t: Throwable) {
                    Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            updateTaskSecondary(updatedName, updatedDescription, updatedCategory, updatedPriority, receivedTask.imagePath)
        }
    }

    private fun updateTaskSecondary(name:String, description:String, category:String, prioraty:String, path:String){
        // Actualiza las propiedades del objeto TodoTask recibido con la nueva imagen
        val updatedTask = receivedTask.copy(
            name = name,
            description = description,
            category = category,
            priority = prioraty,
            imagePath = path
        )

        // Llama al método del ViewModel para actualizar la tarea
        app.updateTodoTasks(updatedTask)
        app.getTodoTasks()
        Toast.makeText(context, "Tarea editada exitosamente", Toast.LENGTH_SHORT).show()
        // Navega de vuelta al fragmento de vista de tareas
        findNavController().navigate(R.id.action_editTaskFragment_to_viewTaskFragment)
    }

    // ===========================   Button delete validation and implemention   ===========================
    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de que deseas eliminar esta tarea?")
            .setPositiveButton("Sí") { dialog, which ->
                deleteTask()
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun deleteTask(){
        app.deleteTodoTasks(receivedTask)
        app.getTodoTasks()
        Toast.makeText(context, "Tarea eliminada exitosamente", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_editTaskFragment_to_viewTaskFragment)
    }

}