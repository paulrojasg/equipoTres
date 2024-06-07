package com.example.project2.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.project2.databinding.FragmentDetailBinding
import com.example.project2.model.TodoTask
import com.example.project2.viewmodel.TodoTaskViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val app: TodoTaskViewModel by viewModels()
    private lateinit var receivedTask: TodoTask

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controladores()
        catchIncomingData()
    }

    private fun controladores() {
        //Lógica para la flecha de atrás
    }

    private fun catchIncomingData(){
        val receivedBundle = arguments
        receivedTask = receivedBundle?.getSerializable("clave") as TodoTask
        // Cargar la imagen desde la URL
        //Editar las variables
//        Glide.with(this)
//            .load(receivedTask.imagePath)
//            .into(binding.petBreedImage)
//        binding.numberAppointmnet.text = "#${receivedAppointment.id.toString()}"
//        binding.titleTextDetailsName.text = receivedAppointment.name_pet
//        binding.petBreedName.text = receivedAppointment.breed
//        binding.ownerPhone.text = "Telefono: ${receivedAppointment.phone_number}"
//        binding.ownerName.text = "Propietario: ${receivedAppointment.name_owner}"
//        binding.petSymptoms.text = receivedAppointment.symptoms
    }

}