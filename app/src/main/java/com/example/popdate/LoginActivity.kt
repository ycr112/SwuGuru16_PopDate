package com.example.popupstoreapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.popdate.DBLogin

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextId: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: TextView

    private lateinit var dbLogin: DBLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 레이아웃 요소 초기화
        editTextId = findViewById(R.id.editTextId)
        editTextPassword = findViewById(R.id.editTextPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        // DBLogin 초기화
        dbLogin = DBLogin(this)

        // 로그인 버튼 클릭 리스너 설정
        btnLogin.setOnClickListener {
            val email = editTextId.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일과 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 데이터베이스를 사용하여 사용자 인증
                if (dbLogin.getUser(email, password)) {
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    // 로그인 성공 시 다른 액티비티로 이동
                    val intent = Intent(this, PopupStoreDetailActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "로그인 실패! 이메일 또는 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 회원가입 버튼 클릭 리스너 설정
        btnRegister.setOnClickListener {
            // 회원가입 액티비티로 이동
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
