package com.zoonmy.animalmbtiapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val data = result.data!!.getStringExtra("resultKey")
                // 결과 처리
            }
        }

        val startButton = findViewById<Button>(R.id.startButton)

        startButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            getResult.launch(intent) // QuizActivity 실행
        }
    }
}