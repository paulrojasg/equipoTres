package com.example.project2.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project2.R
import com.example.project2.databinding.FragmentLoginBinding
import com.example.project2.view.MainActivity
import com.example.project2.view.ProviderType
import com.example.project2.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

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
                loginUser()
            } catch(e: Exception) {
                Toast.makeText(requireContext(), "Conexion fallida", Toast.LENGTH_LONG).show()
            }
        }
        binding.singinButton.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_loginFragment_to_singinFragment)
            } catch(e: Exception) {
                Toast.makeText(requireContext(), "Navegación fallida", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginUser(){
        val email = binding.editTextEmail.text.toString()
        val pass = binding.editTextPassword.text.toString()
        if (email.isNotEmpty() && pass.isNotEmpty()){
            loginViewModel.loginUser(email,pass){ isLogin ->
                if (isLogin){
                    findNavController().navigate(R.id.action_loginFragment_to_viewTaskFragment)
                }else {
                    Toast.makeText(requireContext(), "No se pudo acceder al usuario", Toast.LENGTH_LONG).show()
                }
            }
        } else{
            Toast.makeText(requireContext(), "Verifica que hayas llenado todos los campos correctamente", Toast.LENGTH_LONG).show()
        }
    }

    /*
    private fun goToHome(email: String?, provider: ProviderType){
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(intent)
    }
    */
}