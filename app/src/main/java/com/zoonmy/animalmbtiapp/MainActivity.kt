package com.zoonmy.animalmbtiapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("MBTI_STATS", MODE_PRIVATE)

        // SharedPreferences 총 합계 계산
        val totalParticipants = sharedPreferences.all.values.sumOf { it as Int }

        // TextView에 총 합계 설정
        val participantText = findViewById<TextView>(R.id.participantText)
        participantText.text = "지금까지 ${totalParticipants}명이 참여했어요."

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
