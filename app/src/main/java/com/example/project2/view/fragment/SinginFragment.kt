package com.example.project2.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project2.R
import com.example.project2.databinding.FragmentSinginBinding
import com.example.project2.viewmodel.LoginViewModel

class SinginFragment : Fragment() {

    private lateinit var binding: FragmentSinginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSinginBinding.inflate(inflater)
        binding.lifecycleOwner = this
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
        binding.singinButton.setOnClickListener {
            try {
                registerUser()
            } catch(e: Exception) {
                Toast.makeText(requireContext(), "Conexion fallida", Toast.LENGTH_LONG).show()
            }
        }
        val toolbar : Toolbar = binding.contentToolbar.toolbarSingin
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun registerUser(){
        val email = binding.editTextEmail.text.toString()
        val pass = binding.editTextPassword.text.toString()
        val repass = binding.editTextPassword2.text.toString()
        if (pass == repass){
            if (email.isNotEmpty() && pass.isNotEmpty() && repass.isNotEmpty()){
                loginViewModel.registerUser(email,pass) { isRegister ->
                    if (isRegister) {
                        findNavController().navigate(R.id.action_singinFragment_to_loginFragment)
                    } else {
                        if(pass.length < 6){
                            Toast.makeText(requireContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(requireContext(), "Ya existe una cuenta con este email", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else{
                Toast.makeText(requireContext(), "Verifica que hayas llenado todos los campos correctamente", Toast.LENGTH_LONG).show()
            }
        }
        else {
            Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
        }
    }
}