package com.zoonmy.animalmbtiapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_statistic)

        // MBTI 결과 받기
        val mbtiResult = intent.getStringExtra("MBTI_RESULT") ?: "ENTP"


    }
}