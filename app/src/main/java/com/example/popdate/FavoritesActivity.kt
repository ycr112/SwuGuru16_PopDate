package com.example.popdate

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class FavoritesActivity : AppCompatActivity() {

    private lateinit var favoritesRepository: FavoritesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        favoritesRepository = FavoritesRepository(this)

        val favoritesListView = findViewById<ListView>(R.id.favoritesListView)
        val favorites = favoritesRepository.getFavorites()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, favorites)
        favoritesListView.adapter = adapter
    }
}
