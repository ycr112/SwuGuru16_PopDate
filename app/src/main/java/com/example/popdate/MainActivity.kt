package com.example.popdate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var notificationButton: Button
    private lateinit var categoryButton: Button
    private lateinit var searchButton: Button
    private lateinit var myPageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.notificationButton).setOnClickListener {
            // 알림 버튼 클릭 시 처리
            showNotification()
        }

        findViewById<Button>(R.id.categoryButton).setOnClickListener {
            // 카테고리 버튼 클릭 시 처리
            navigateToCategory()
        }

        findViewById<Button>(R.id.searchButton).setOnClickListener {
            // 검색하기 버튼 클릭 시 처리
            navigateToSearch()
        }

        findViewById<Button>(R.id.myPageButton).setOnClickListener {
            // 마이페이지 버튼 클릭 시 처리
            navigateToMyPage()
        }
    }

    private fun showNotification() {
        // 알림을 표시하는 코드
        // Toast.makeText(this, "알림 버튼 클릭됨", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCategory() {
        // 카테고리 화면으로 이동하는 코드
        // startActivity(Intent(this, CategoryActivity::class.java))
    }

    private fun navigateToSearch() {
        // 검색 화면으로 이동하는 코드
        // startActivity(Intent(this, SearchActivity::class.java))
    }

    private fun navigateToMyPage() {
        // 마이페이지 화면으로 이동하는 코드
        startActivity(Intent(this, MyPageActivity::class.java))
    }
}