package com.example.popdate

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {

    private lateinit var regionConfirmButton: Button
    private lateinit var regionSearchEditText: EditText
    private lateinit var categoryClothing: CheckBox
    private lateinit var categoryAnimation: CheckBox
    private lateinit var categoryDessert: CheckBox
    private lateinit var categoryIdol: CheckBox
    private lateinit var categoryOther: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // 뷰 초기화
        regionConfirmButton = findViewById(R.id.regionConfirmButton)
        regionSearchEditText = findViewById(R.id.regionSearchEditText)
        categoryClothing = findViewById(R.id.categoryClothing)
        categoryAnimation = findViewById(R.id.categoryAnimation)
        categoryDessert = findViewById(R.id.categoryDessert)
        categoryIdol = findViewById(R.id.categoryIdol)
        categoryOther = findViewById(R.id.categoryOther)

        // 버튼 클릭 리스너 설정
        regionConfirmButton.setOnClickListener {
            // EditText에서 지역 이름 가져오기
            val region = regionSearchEditText.text.toString()
            // 선택된 카테고리 가져오기
            val selectedCategory = getSelectedField()
            // 지역 이름과 선택된 카테고리를 Toast로 표시
            Toast.makeText(this, "Region: $region\nSelected Category: $selectedCategory", Toast.LENGTH_LONG).show()
        }
    }

    // 선택된 카테고리를 반환하는 함수
    private fun getSelectedField(): String {
        return when {
            categoryClothing.isChecked -> "Clothing"  // 의류가 선택된 경우
            categoryAnimation.isChecked -> "Animation"  // 애니메이션이 선택된 경우
            categoryDessert.isChecked -> "Dessert"  // 디저트가 선택된 경우
            categoryIdol.isChecked -> "Idol"  // 아이돌이 선택된 경우
            categoryOther.isChecked -> "Other"  // 기타가 선택된 경우
            else -> "None"  // 아무것도 선택되지 않은 경우
        }
    }
}