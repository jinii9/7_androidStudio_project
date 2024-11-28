package com.zoonmy.animalmbtiapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0 // 현재 질문의 인덱스
    private val results = mutableMapOf(
        "E" to 0, "I" to 0,
        "N" to 0, "S" to 0,
        "T" to 0, "F" to 0,
        "J" to 0, "P" to 0
    )

    enum class Dimension(val label: String) {
        EI("E/I"),
        NS("N/S"),
        TF("T/F"),
        JP("J/P");
    }

    private val questions = listOf(
        "어떤 자리에서도 사람들과 \n잘 어울리는 편이야?" to Dimension.EI,
        "새로운 사람 만나는 거 좋아해?" to Dimension.EI,
        "열린 결말의 영화를 좋아해?" to Dimension.NS,
        "싸울 때 잘잘못을 먼저 따지는 편이야?" to Dimension.TF,
        "새로운 사람과 대화할 때\n 말을 많이 하는 편이야?" to Dimension.EI,
        "너는 상상력이 풍부해?" to Dimension.NS,
        "주말에 쉬고 있는데 갑자기\n 친구가 나오라고 하면 나가?" to Dimension.EI,
        "갑자기 계획 바뀌면 \n스트레스 받는 편이야?" to Dimension.JP,
        "일상적인 루틴을 잘 지키는 편이야?" to Dimension.JP,
        "친구들이랑 대화 중에 '만약에...'라는 질문을 많이 해?" to Dimension.NS,
        "다른 사람들이 뭐라고 하든, 하고 싶은 거 하는 편이야?" to Dimension.TF,
        "팀플할 때, 분위기보다는\n효율성과 결과를 더 중요하게 생각해?" to Dimension.TF,
        "사람들과 함께 있으면\n 에너지가 더 생긴다고 느껴?" to Dimension.EI,
        "모든 일을 완벽하게 끝내야 \n다음으로 넘어가는 편이야?" to Dimension.JP
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val textViewProgress = findViewById<TextView>(R.id.textView)
        val textViewQuestion = findViewById<TextView>(R.id.textView2)
        val buttonYes = findViewById<Button>(R.id.button)
        val buttonNo = findViewById<Button>(R.id.button2)

        // ProgressBar 설정
        progressBar.max = questions.size

        // 첫 질문 표시
        updateQuestion(textViewQuestion, textViewProgress, progressBar)

        // "응" 버튼 클릭 시
        buttonYes.setOnClickListener {
            updateResult(true) // "예" 선택
            nextQuestion(textViewQuestion, textViewProgress, progressBar)
        }

        // "아니" 버튼 클릭 시
        buttonNo.setOnClickListener {
            updateResult(false) // "아니오" 선택
            nextQuestion(textViewQuestion, textViewProgress, progressBar)
        }
    }


    private fun updateQuestion(
        textViewQuestion: TextView,
        textViewProgress: TextView,
        progressBar: ProgressBar
    ) {
        if (currentQuestionIndex < questions.size) {
            val (question, _) = questions[currentQuestionIndex]
            textViewQuestion.text = question // 현재 질문 표시
            textViewProgress.text = getString(R.string.progress_text, currentQuestionIndex + 1, questions.size) // 진행률 표시
            progressBar.progress = currentQuestionIndex + 1
        } else {
            showResult() // 모든 질문이 끝난 경우 결과 표시
        }
    }

    private fun updateResult(isYes: Boolean) {
        val (_, dimension) = questions[currentQuestionIndex] // 현재 질문의 차원 가져오기
        when (dimension) {
            Dimension.EI -> if (isYes) results["E"] = results["E"]!! + 1 else results["I"] = results["I"]!! + 1
            Dimension.NS -> if (isYes) results["N"] = results["N"]!! + 1 else results["S"] = results["S"]!! + 1
            Dimension.TF -> if (isYes) results["T"] = results["T"]!! + 1 else results["F"] = results["F"]!! + 1
            Dimension.JP -> if (isYes) results["J"] = results["J"]!! + 1 else results["P"] = results["P"]!! + 1
        }
    }

    private fun nextQuestion(
        textViewQuestion: TextView,
        textViewProgress: TextView,
        progressBar: ProgressBar
    ) {
        currentQuestionIndex++ // 다음 질문으로 이동
        updateQuestion(textViewQuestion, textViewProgress, progressBar)
    }

    private fun showResult() {
        val mbtiResult = calculateMBTI()
        Log.d("QuizActivity", "MBTI Result: $mbtiResult")
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("MBTI_RESULT", mbtiResult)
        startActivity(intent)
        finish()
    }

    private fun calculateMBTI(): String {
        return buildString {
            append(if (results["E"]!! >= results["I"]!!) "E" else "I")
            append(if (results["N"]!! >= results["S"]!!) "N" else "S")
            append(if (results["T"]!! >= results["F"]!!) "T" else "F")
            append(if (results["J"]!! >= results["P"]!!) "J" else "P")
        }
    }
}