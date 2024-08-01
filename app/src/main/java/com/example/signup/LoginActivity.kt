package com.example.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signupEmailButton: Button
    private lateinit var snsLoginButton: Button
    private lateinit var snsSignupButton: Button
    private lateinit var loginButtonBottom: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 뷰 초기화
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signupEmailButton = findViewById(R.id.signupEmailButton)
        snsLoginButton = findViewById(R.id.snsLoginButton)
        snsSignupButton = findViewById(R.id.snsSignupButton)
        loginButtonBottom = findViewById(R.id.loginButtonBottom)

        // 데이터베이스 인스턴스 가져오기
        val db = AppDatabase.getDatabase(this)
        userDao = db.userDao()

        // 로그인 버튼 클릭 리스너 설정
        signupEmailButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // 실제 로그인 검증 로직을 호출
            login(username, password)
        }

        // 회원가입 버튼 클릭 리스너 설정
        snsLoginButton.setOnClickListener {
            // 회원가입 화면으로 이동
            Toast.makeText(this, "SNS 로그인 기능은 아직 구현되지 않았습니다", Toast.LENGTH_SHORT).show()
        }

        // SNS 로그인 버튼 클릭 리스너 설정
        snsSignupButton.setOnClickListener {
            // SNS 로그인 로직을 여기에 추가하세요.
            Toast.makeText(this, "SNS 로그인 기능은 아직 구현되지 않았습니다", Toast.LENGTH_SHORT).show()
        }

        // SNS 회원가입 버튼 클릭 리스너 설정
        loginButtonBottom.setOnClickListener {
            // SNS 회원가입 로직을 여기에 추가하세요.
            Toast.makeText(this, "SNS 회원가입 기능은 아직 구현되지 않았습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun login(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userDao.getUser(username, password)
            withContext(Dispatchers.Main) {
                if (user != null) {
                    // 로그인 성공 시 메인 화면으로 이동
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // 로그인 실패 시 에러 메시지 표시
                    Toast.makeText(this@LoginActivity, "아이디나 비밀번호가 존재하지 않습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
