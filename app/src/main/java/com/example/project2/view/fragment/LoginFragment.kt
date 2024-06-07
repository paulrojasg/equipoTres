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
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private val GOOGLE_SIGN_IN = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        isSession()
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
        binding.editButtonGoogle.setOnClickListener {
            try {
                val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                val googleClient = GoogleSignIn.getClient(requireActivity(), googleConf)
                googleClient.signOut()
                startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
            } catch(e: Exception) {
                Toast.makeText(requireContext(), "No se pudo acceder a Google", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginUser(){
        val email = binding.editTextEmail.text.toString()
        val pass = binding.editTextPassword.text.toString()
        if (email.isNotEmpty() && pass.isNotEmpty()){
            loginViewModel.loginUser(email,pass){ isLogin ->
                if (isLogin){
                    //Guardar la session
                    val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                    sharedPreferences.putString("email", email)
                    sharedPreferences.apply()

                    findNavController().navigate(R.id.action_loginFragment_to_viewTaskFragment)
                }else {
                    Toast.makeText(requireContext(), "No se pudo acceder al usuario", Toast.LENGTH_LONG).show()
                }
            }
        } else{
            Toast.makeText(requireContext(), "Verifica que hayas llenado todos los campos correctamente", Toast.LENGTH_LONG).show()
        }
    }
    private fun isSession(){
        val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        loginViewModel.session(email){ isEnableView ->
            if (isEnableView){
                findNavController().navigate(R.id.action_loginFragment_to_viewTaskFragment)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)
                if(account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                        if(it.isSuccessful){
                            findNavController().navigate(R.id.action_loginFragment_to_viewTaskFragment)
                        } else {
                            Toast.makeText(requireContext(), "No pudimos autentificar tu cuenta", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e : ApiException){
                val errorMessage = e.message ?: "Error en el catch"
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(), "Error en el catch", Toast.LENGTH_LONG).show()
            }
        }
    }
}