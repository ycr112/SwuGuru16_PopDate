package com.example.popdate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class MyPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        findViewById<Button>(R.id.userInfoButton).setOnClickListener {
            viewUserInfo()
        }

        findViewById<Button>(R.id.myFavoritesButton).setOnClickListener {
            viewMyFavorites()
        }

        val notificationConsent = findViewById<CheckBox>(R.id.notificationConsent)
        notificationConsent.setOnCheckedChangeListener { _, isChecked ->
            handleNotificationConsent(isChecked)
        }
    }

    private fun viewUserInfo() {
        // 회원 정보를 보여주는 코드
        // 예를 들어, 새로운 액티비티를 열거나 다이얼로그를 표시
        //startActivity(Intent(this, UserInfoActivity::class.java))
    }

    private fun viewMyFavorites() {
        // 찜한 목록을 보여주는 코드
        // 예를 들어, 찜한 목록 액티비티를 열거나 다이얼로그를 표시
        startActivity(Intent(this, FavoritesActivity::class.java))
    }

    private fun handleNotificationConsent(isChecked: Boolean) {
        // 알림 수신 동의 상태 처리
        if (isChecked) {
            // 알림 수신 동의 상태
            Log.d("MyPageActivity", "알림 수신 동의됨")
        } else {
            // 알림 수신 비동의 상태
            Log.d("MyPageActivity", "알림 수신 동의 안함")
        }
    }
}

