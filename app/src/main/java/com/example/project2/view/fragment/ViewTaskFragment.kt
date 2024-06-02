package com.example.project2.view.fragment

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
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
import com.example.project2.databinding.FragmentViewtaskBinding

class ViewTaskFragment : Fragment() {

    private lateinit var binding: FragmentViewtaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewtaskBinding.inflate(inflater)

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

    }


}
