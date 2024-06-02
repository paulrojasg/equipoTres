package com.example.project2.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project2.R

class ViewTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_viewtask, container, false)
    }

    private fun controladores() {
        bingin
        binding.button1.setOnClickListener {
            try {
                Log.e("Exito","It works")
                findNavController().navigate(R.id.action_homeFragment_to_createFragment)
            } catch(e: Exception) {
                Log.e("Error","Navegación fallida",e)
            }
        }

    }
}
