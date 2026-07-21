package com.example.edekaqaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.edekaqaapp.ui.EdekaQaApp
import com.example.edekaqaapp.ui.theme.EdekaQaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdekaQaAppTheme {
                EdekaQaApp()
            }
        }
    }
}