package com.zoonmy.animalmbtiapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val buttonLook = findViewById<Button>(R.id.buttonLook)
        buttonLook.setOnClickListener {
            startActivity(Intent(this, AllResultsActivity::class.java))
        }

    }
}