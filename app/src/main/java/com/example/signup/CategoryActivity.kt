package com.example.signup

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
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


        regionConfirmButton = findViewById(R.id.regionConfirmButton)
        regionSearchEditText = findViewById(R.id.regionSearchEditText)
        categoryClothing = findViewById(R.id.categoryClothing)
        categoryAnimation = findViewById(R.id.categoryAnimation)
        categoryDessert = findViewById(R.id.categoryDessert)
        categoryIdol = findViewById(R.id.categoryIdol)
        categoryOther = findViewById(R.id.categoryOther)


        regionConfirmButton.setOnClickListener {
            val region = regionSearchEditText.text.toString()

        }
    }


    fun getSelectedField(): String {
        return when {
            categoryClothing.isChecked -> "Clothing"
            categoryAnimation.isChecked -> "Animation"
            categoryDessert.isChecked -> "Dessert"
            categoryIdol.isChecked -> "Idol"
            categoryOther.isChecked -> "Other"
            else -> ""
        }
    }
}
