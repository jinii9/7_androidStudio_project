package com.zoonmy.animalmbtiapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AllResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_results)

        val buttonClose = findViewById<ImageButton>(R.id.buttonClose)
        buttonClose.setOnClickListener {
            finish()
        }
    }
}