package com.zoonmy.animalmbtiapp

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultStatisticActivity : AppCompatActivity() {
    private val textViewIds = listOf(
        R.id.enfpTextView,
        R.id.entpTextView,
        R.id.enfjTextView,
        R.id.entjTextView,
        R.id.infpTextView,
        R.id.intpTextView,
        R.id.infjTextView,
        R.id.intjTextView,
        R.id.esfpTextView,
        R.id.estpTextView,
        R.id.esfjTextView,
        R.id.estjTextView,
        R.id.isfpTextView,
        R.id.istpTextView,
        R.id.isfjTextView,
        R.id.istjTextView
    )

    private val mbtiTypes = listOf(
        "ENFP", "ENTP", "ENFJ", "ENTJ",
        "INFP", "INTP", "INFJ", "INTJ",
        "ESFP", "ESTP", "ESFJ", "ESTJ",
        "ISFP", "ISTP", "ISFJ", "ISTJ"
    )

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_statistic)

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("MBTI_STATS", MODE_PRIVATE)

        // MBTI 결과 받기
        val mbtiResult = intent.getStringExtra("MBTI_RESULT") ?: "ENTP"

        // 획득한 사람 수 업데이트
        updateCount(mbtiResult)

        // TextView와 MBTI 타입 매핑
        for (i in textViewIds.indices) {
            val textView = findViewById<TextView>(textViewIds[i])
            val count = sharedPreferences.getInt(mbtiTypes[i], 0) // 저장된 값 가져오기
            textView.text = "획득한 사람 수 : ${count}명"
        }
    }

    private fun updateCount(mbtiResult: String) {
        // 현재 획득한 사람 수 가져오기
        val currentCount = sharedPreferences.getInt(mbtiResult, 0)

        // 획득한 사람 수 증가
        val newCount = currentCount + 1

        // SharedPreferences에 저장
        val success = sharedPreferences.edit().putInt(mbtiResult, newCount).commit()
        if (!success) {
            println("SharedPreferences 저장 실패")
        }
    }
}
