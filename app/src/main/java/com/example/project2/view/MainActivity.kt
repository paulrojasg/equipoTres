package com.example.project2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project2.R

enum class ProviderType {
    BASIC,
    GOOGLE
}
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}