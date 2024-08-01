package com.example.popdate

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserInfoActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)

        userRepository = UserRepository(this)

        val user = userRepository.getUser()
        val userNameTextView = findViewById<TextView>(R.id.userNameTextView)
        val userEmailTextView = findViewById<TextView>(R.id.userEmailTextView)

        if (user != null) {
            userNameTextView.text = "회원 이름: ${user.name}"
            userEmailTextView.text = "회원 이메일: ${user.email}"
        } else {
            userNameTextView.text = "회원 이름: 없음"
            userEmailTextView.text = "회원 이메일: 없음"
        }
    }
}
