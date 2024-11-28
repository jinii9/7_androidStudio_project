package com.zoonmy.animalmbtiapp

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

class ResultActivity : AppCompatActivity() {
    private val STORAGE_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // MBTI 결과 받기
        val mbtiResult = intent.getStringExtra("MBTI_RESULT") ?: "ENTP"
        val imageView = findViewById<ImageView>(R.id.imageView)

        // MBTI
        var textViewMbti = findViewById<TextView>(R.id.textViewMbti)
        textViewMbti.text = mbtiResult

        // 메인으로 이동
        val buttonRefresh = findViewById<ImageButton>(R.id.buttonRefresh)
        buttonRefresh.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        // 통계판으로 이동
        val buttonStatistics = findViewById<Button>(R.id.buttonStatistics)
        buttonStatistics.setOnClickListener {
            // MBTI 결과값 전달
            intent.putExtra("MBTI_RESULT", mbtiResult)
            startActivity(intent)

            startActivity(Intent(this, ResultStatisticActivity::class.java))
        }


        // MBTI 결과에 따라 이미지 설정
        val imageResource = when(mbtiResult) {
            "ISTJ" -> R.drawable.istj
            "ISFJ" -> R.drawable.isfj
            "INFJ" -> R.drawable.infj
            "INTJ" -> R.drawable.intj
            "ISTP" -> R.drawable.istp
            "ISFP" -> R.drawable.isfp
            "INFP" -> R.drawable.infp
            "INTP" -> R.drawable.intp
            "ESTP" -> R.drawable.estp
            "ESFP" -> R.drawable.esfp
            "ENFP" -> R.drawable.enfp
            "ENTP" -> R.drawable.entp
            "ESTJ" -> R.drawable.estj
            "ESFJ" -> R.drawable.esfj
            "ENFJ" -> R.drawable.enfj
            "ENTJ" -> R.drawable.entj
            else -> R.drawable.entp
        }
        // 이미지 설정
        imageView.setImageResource(imageResource)


        // 모든 결과 유형 보기
        val buttonLook = findViewById<Button>(R.id.buttonLook)
        buttonLook.setOnClickListener {
            startActivity(Intent(this, AllResultsActivity::class.java))
        }
        // 로컬 이미지 저장
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            if (checkPermission()) {
                saveImage()
            } else {
                requestPermission()
            }
        }

    }

    // 권한 메서드
    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            true
        } else {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        } else {
            saveImage()
        }
    }

    // 이미지 저장 메서드
    private fun saveImage() {
        val imageView = findViewById<ImageView>(R.id.imageView)
        val drawable = imageView.drawable
        val bitmap = (drawable as BitmapDrawable).bitmap // 이미지뷰에서 비트맵 가져오기

        val fileName = "MBTI_Image_${System.currentTimeMillis()}.jpg"

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Android 10 이상: MediaStore API 사용
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let { uri ->
                    contentResolver.openOutputStream(uri)?.use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                }
            } else {  // Android 9 이하: 직접 파일 생성
                val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, fileName)
                FileOutputStream(image).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
            }

            Toast.makeText(this, "이미지가 저장되었습니다", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "저장 실패: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    // 권한 요청 결과 처리
    // 승인되면 이미지 저장, 거부되면 메시지 표시
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage()
            } else {
                Toast.makeText(this, "권한이 필요합니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
