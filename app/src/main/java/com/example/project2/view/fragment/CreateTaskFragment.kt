package com.example.project2.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.project2.R
import com.google.firebase.firestore.FirebaseFirestore
class CreateTaskFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myButton = view.findViewById<Button>(R.id.button)

        myButton.setOnClickListener {
            Toast.makeText(context, "Hola a todos", Toast.LENGTH_SHORT).show()

            db.collection("TestCollection").document("TestDocument2").set(
                hashMapOf(
                    "codigo" to "65465",
                    "nombre" to "GG",
                )
            )
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_task, container, false)
    }

}