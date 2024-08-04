package com.example.popdate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 로그인 상태 확인 (예시: SharedPreferences 사용)
        val sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            // 로그인되지 않은 경우 LoginActivity로 전환
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        // 버튼 초기화
        val notificationButton = findViewById<Button>(R.id.notificationButton)
        val categoryButton = findViewById<Button>(R.id.categoryButton)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val myPageButton = findViewById<Button>(R.id.myPageButton)

        // 버튼 클릭 리스너 설정
        notificationButton.setOnClickListener {
            // 알림 화면으로 전환
            // startActivity(Intent(this, NotificationActivity::class.java))
        }
        categoryButton.setOnClickListener {
            // 카테고리 화면으로 전환
            startActivity(Intent(this, CategoryActivity::class.java))
        }
        searchButton.setOnClickListener {
            // 검색 화면으로 전환
            startActivity(Intent(this, SearchActivity::class.java))
        }
        myPageButton.setOnClickListener {
            // 마이페이지 화면으로 전환
            startActivity(Intent(this, MyPageActivity::class.java))
        }
    }
}
