package com.example.project2.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project2.R
import com.example.project2.databinding.CardTaskBinding
import com.example.project2.databinding.FragmentViewtaskBinding
import com.example.project2.model.TodoTask
import com.example.project2.view.adapter.TaskAdapter
import com.example.project2.viewmodel.TodoTaskViewModel
import com.google.firebase.auth.FirebaseAuth

class ViewTaskFragment : Fragment() {

    private lateinit var binding: FragmentViewtaskBinding
    private lateinit var binding_card: CardTaskBinding
    private val app: TodoTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewtaskBinding.inflate(inflater)
        binding_card = CardTaskBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    controladores()
    observadorViewModel()
    val callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            activity?.moveTaskToBack(true)
        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
}


    private fun controladores() {
        binding.buttonAdd.setOnClickListener {
            try {
                Log.e("Exito","It works")
                findNavController().navigate(R.id.action_fragment_viewtask_to_createTaskFragment)
            } catch(e: Exception) {
                Log.e("Error","Navegación fallida",e)
            }
        }
        binding.imageButton4.setOnClickListener {
            try{
                //Borrar la session
                val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                sharedPreferences.clear()
                sharedPreferences.apply()
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(R.id.action_fragment_viewtask_to_loginFragment)
            }catch(e: Exception){
                Log.e("Error","Navegación fallida",e)
            }
        }
    }

    private fun observadorViewModel(){
        observerListTask()
        observerProgress()
    }

    private fun observerListTask(){
        app.listTodoTask.observe(viewLifecycleOwner) { listTodoTask ->
            Log.d("Lista de tareas observadas:", listTodoTask.toString())
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = TaskAdapter(listTodoTask, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
            Log.d("RecyclerView", "Adapter Set with ${listTodoTask.size} tasks")
        }
    }

    private fun observerProgress(){
        app.progressState.observe(viewLifecycleOwner){status ->
            binding.progress.isVisible = status
        }
    }

}


