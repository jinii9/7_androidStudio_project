package com.zoonmy.animalmbtiapp

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

    private val questions = listOf(
        "어떤 자리에서도 사람들과 잘 어울리는 편이야?" to "E/I",
        "새로운 사람 만나는 거 좋아해?" to "E/I",
        "열린 결말의 영화를 좋아해?" to "N/S",
        "싸울 때 잘잘못을 먼저 따지는 편이야?" to "T/F",
        "새로운 사람과 대화할 때 말을 많이 하는 편이야?" to "E/I",
        "너는 상상력이 풍부해?" to "N/S",
        "주말에 쉬고 있는데 갑자기 친구가 나오라고 하면 나가?" to "E/I",
        "갑자기 계획 바뀌면 스트레스 받는 편이야?" to "J/P",
        "일상적인 루틴을 잘 지키는 편이야?" to "J/P",
        "친구들이랑 대화 중에 '만약에...'라는 질문을 많이 해?" to "N/S",
        "다른 사람들이 뭐라고 하든, 하고 싶은 거 하는 편이야?" to "T/F",
        "팀플할 때, 분위기보다는\n효율성과 결과를 더 중요하게 생각해?" to "T/F",
        "사람들과 함께 있으면 에너지가 더 생긴다고 느껴?" to "E/I",
        "모든 일을 완벽하게 끝내야 다음으로 넘어가는 편이야?" to "J/P"
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
            textViewProgress.text = "${currentQuestionIndex + 1} / ${questions.size}" // 진행률 표시
            progressBar.progress = currentQuestionIndex + 1
        } else {
            showResult() // 모든 질문이 끝난 경우 결과 표시
        }
    }

    private fun updateResult(isYes: Boolean) {
        val (_, dimension) = questions[currentQuestionIndex] // 현재 질문의 차원 가져오기
        when (dimension) {
            "E/I" -> if (isYes) results["E"] = results["E"]!! + 1 else results["I"] = results["I"]!! + 1
            "N/S" -> if (isYes) results["N"] = results["N"]!! + 1 else results["S"] = results["S"]!! + 1
            "T/F" -> if (isYes) results["T"] = results["T"]!! + 1 else results["F"] = results["F"]!! + 1
            "J/P" -> if (isYes) results["J"] = results["J"]!! + 1 else results["P"] = results["P"]!! + 1
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