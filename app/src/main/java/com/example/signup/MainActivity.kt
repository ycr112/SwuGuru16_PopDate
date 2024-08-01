package com.example.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.signup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 하단 버튼 클릭 리스너 설정
        binding.searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.categoryButton.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }



        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // 클릭 이벤트 처리 메서드
    fun onSearchIconClick(view: View) {
        // 검색어 입력 화면으로 이동
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)


        // 예제: 검색 아이콘 클릭 시 간단한 토스트 메시지 표시
        Toast.makeText(this, "검색 아이콘 클릭됨", Toast.LENGTH_SHORT).show()
    }
}
