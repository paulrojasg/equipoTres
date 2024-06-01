package com.example.project2.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.project2.R
import com.example.project2.viewmodel.TodoTaskViewModel
import com.google.firebase.firestore.FirebaseFirestore
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import com.example.project2.model.TodoTask
import com.example.project2.repository.TodoTaskRepository
import kotlin.reflect.typeOf


class CreateTaskFragment : Fragment() {

    private val app: TodoTaskViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myButton = view.findViewById<Button>(R.id.button)
        val myButton2 = view.findViewById<Button>(R.id.button2)

        myButton.setOnClickListener {
            app.getTodoTasks()
        }

        myButton2.setOnClickListener {

            val x = TodoTask(
                "TWrTBhxftN6dmFsUIYln",
                "hello_there",
                "I'm really happy right now",
                "family",
                "2"
            )

            app.updateTodoTasks(x)
            app.getTodoTasks()




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