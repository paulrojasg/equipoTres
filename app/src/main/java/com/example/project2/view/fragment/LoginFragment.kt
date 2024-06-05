package com.example.project2.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project2.R
import com.example.project2.databinding.FragmentLoginBinding
import com.example.project2.viewmodel.TodoTaskViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity?.moveTaskToBack(true)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun controladores() {
        binding.loginButton.setOnClickListener {
            try {
                Log.d("Exito","It works")
                findNavController().navigate(R.id.action_loginFragment_to_viewTaskFragment)
            } catch(e: Exception) {
                Log.e("Error","Navegación fallida de task",e)
            }
        }
        binding.singinButton.setOnClickListener {
            try {
                Log.d("Exito","It works")
                findNavController().navigate(R.id.action_loginFragment_to_singinFragment)
            } catch(e: Exception) {
                Log.e("Error","Navegación fallida de task",e)
            }
        }
    }

}