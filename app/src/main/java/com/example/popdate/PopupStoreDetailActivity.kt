package com.example.popupstoreapp

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.popdate.DBPopupStoreDetail

class PopupStoreDetailActivity : AppCompatActivity() {

    private lateinit var storeImage: ImageView
    private lateinit var storeName: TextView
    private lateinit var storeDescription: TextView
    private lateinit var storePeriod: TextView
    private lateinit var storeLocation: TextView
    private lateinit var storeLinkButton: Button
    private lateinit var wishlistButton: ImageButton
    private lateinit var commentInput: EditText
    private lateinit var sendCommentButton: Button
    private lateinit var commentsListView: ListView

    private lateinit var databaseHelper: DBPopupStoreDetail
    private lateinit var db: SQLiteDatabase
    private lateinit var commentsAdapter: ArrayAdapter<String>
    private val commentsList = mutableListOf<String>()

    private var storeId: String = "sampleStoreId" // 예시로 고정된 storeId 값 사용, 실제 구현에서는 동적으로 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popupstoredetails)

        // 레이아웃 요소 초기화
        storeImage = findViewById(R.id.storeImage)
        storeName = findViewById(R.id.storeName)
        storeDescription = findViewById(R.id.storeDescription)
        storePeriod = findViewById(R.id.storePeriod)
        storeLocation = findViewById(R.id.storeLocation)
        storeLinkButton = findViewById(R.id.storeLinkButton)
        wishlistButton = findViewById(R.id.wishlistButton)
        commentInput = findViewById(R.id.commentInput)
        sendCommentButton = findViewById(R.id.sendCommentButton)
        commentsListView = findViewById(R.id.commentsListView)

        // DatabaseHelper 초기화
        databaseHelper = DBPopupStoreDetail(this)
        db = databaseHelper.writableDatabase

        // 데이터 설정 (테스트 데이터 사용)
        storeName.text = "팝업스토어 이름"
        storeDescription.text = "팝업스토어 소개 내용입니다."
        storePeriod.text = "운영 기간: 2024-07-01 ~ 2024-07-31"
        storeLocation.text = "위치: 서울시 강남구"
        storeLinkButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://example.com") // 실제 링크로 교체
            }
            startActivity(intent)
        }

        // 위시리스트 버튼 클릭 리스너 설정
        wishlistButton.setOnClickListener {
            if (isWishListed(storeId)) {
                wishListDelete(storeId)
            } else {
                wishListAdd(storeId)
            }
        }

        // 초기 위시리스트 상태 설정
        updateWishlistButton()

        // 댓글 리스트 초기화
        commentsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, commentsList)
        commentsListView.adapter = commentsAdapter

        // 댓글 불러오기
        loadComments()

        // 댓글 달기 버튼 클릭 리스너 설정
        sendCommentButton.setOnClickListener {
            val comment = commentInput.text.toString()
            if (comment.isNotEmpty()) {
                addComment(comment)
                commentInput.text.clear()
            } else {
                Toast.makeText(this, "댓글을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun wishListAdd(storeId: String) {
        val values = ContentValues().apply {
            put(DBPopupStoreDetail.COLUMN_STORE_ID, storeId)
        }
        db.insert(DBPopupStoreDetail.TABLE_POPUP_STORES, null, values)
        Toast.makeText(this, "위시리스트에 추가되었습니다!", Toast.LENGTH_SHORT).show()
        updateWishlistButton()
    }

    private fun wishListDelete(storeId: String) {
        val selection = "${DBPopupStoreDetail.COLUMN_STORE_ID} = ?"
        val selectionArgs = arrayOf(storeId)
        db.delete(DBPopupStoreDetail.TABLE_POPUP_STORES, selection, selectionArgs)
        Toast.makeText(this, "위시리스트에서 삭제되었습니다!", Toast.LENGTH_SHORT).show()
        updateWishlistButton()
    }

    private fun isWishListed(storeId: String): Boolean {
        val selection = "${DBPopupStoreDetail.COLUMN_STORE_ID} = ?"
        val selectionArgs = arrayOf(storeId)
        val cursor = db.query(
            DBPopupStoreDetail.TABLE_POPUP_STORES,
            arrayOf(DBPopupStoreDetail.COLUMN_STORE_ID),
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }

    private fun updateWishlistButton() {
        if (isWishListed(storeId)) {
            wishlistButton.setImageResource(R.mipmap.ic_like_filled_foreground)
        } else {
            wishlistButton.setImageResource(R.mipmap.ic_like_foreground)
        }
    }

    private fun addComment(comment: String) {
        val values = ContentValues().apply {
            put(DBPopupStoreDetail.COLUMN_STORE_ID, storeId)
            put(DBPopupStoreDetail.COLUMN_COMMENT_TEXT, comment)
        }
        db.insert(DBPopupStoreDetail.TABLE_COMMENTS, null, values)
        loadComments()
    }

    private fun loadComments() {
        commentsList.clear()
        val selection = "${DBPopupStoreDetail.COLUMN_STORE_ID} = ?"
        val selectionArgs = arrayOf(storeId)
        val cursor = db.query(
            DBPopupStoreDetail.TABLE_COMMENTS,
            arrayOf(DBPopupStoreDetail.COLUMN_COMMENT_TEXT),
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                val comment = getString(getColumnIndexOrThrow(DBPopupStoreDetail.COLUMN_COMMENT_TEXT))
                commentsList.add(comment)
            }
        }
        cursor.close()
        commentsAdapter.notifyDataSetChanged()
    }
}
