package com.zoonmy.animalmbtiapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultStatisticActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result_statistic)


        val enfpTextView = findViewById<TextView>(R.id.enfpTextView)
        val entpTextView = findViewById<TextView>(R.id.entpTextView)
    }
}