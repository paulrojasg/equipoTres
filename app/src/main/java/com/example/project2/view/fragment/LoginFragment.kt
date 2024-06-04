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
    private val app: TodoTaskViewModel by viewModels()

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
                Log.e("Exito","It works")
                findNavController().navigate(R.id.action_fragment_viewlogin_to_taskFragment)
            } catch(e: Exception) {
                Log.e("Error","Navegación fallida",e)
            }
        }
        binding.singinButton.setOnClickListener {
            try{
                Log.e("Exito","It works")
                findNavController().navigate(R.id.action_fragment_viewlogin_to_singinFragment)
            }catch(e: Exception){
                Log.e("Error","Navegación fallida",e)
            }
        }
    }

}