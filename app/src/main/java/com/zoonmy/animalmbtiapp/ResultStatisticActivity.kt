package com.zoonmy.animalmbtiapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultStatisticActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_statistic)

        // MBTI 결과 받기
        val mbtiResult = intent.getStringExtra("MBTI_RESULT") ?: "ENTP"


    }
}