package com.example.popdate

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions



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

    private var storeId: String? = null
    private var imm: InputMethodManager? = null
    private var cl: ConstraintLayout? = null

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
        cl = findViewById(R.id.cl) // ConstraintLayout 초기화

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        cl?.setOnClickListener {
            imm?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        // 데이터베이스 설정
        databaseHelper = DBPopupStoreDetail(this)
        db = databaseHelper.writableDatabase

        // Intent로부터 storeId 가져오기
        storeId = intent.getStringExtra("STORE_ID")

        if (storeId != null) {
            loadStoreData(storeId!!)
            loadComments()
        }

        // 링크 버튼 클릭 리스너 설정
        storeLinkButton.setOnClickListener {
            val link = getStoreLink(storeId!!)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }

        // 위시리스트 버튼 클릭 리스너 설정
        wishlistButton.setOnClickListener {
            if (storeId != null) {
                if (isWishListed(storeId!!)) {
                    wishListDelete(storeId!!)
                } else {
                    wishListAdd(storeId!!)
                }
            }
        }

        // 댓글 리스트 초기화
        commentsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, commentsList)
        commentsListView.adapter = commentsAdapter

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

        // EditText의 IME_ACTION_DONE 동작 처리
        commentInput.setOnEditorActionListener { textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_DONE) {
                imm?.hideSoftInputFromWindow(commentInput.windowToken, 0)
                handled = true
            }
            handled
        }
    }

    private fun loadStoreData(storeId: String) {
        val selection = "${DBPopupStoreDetail.COLUMN_STORE_ID} = ?"
        val selectionArgs = arrayOf(storeId)
        val cursor = db.query(
            DBPopupStoreDetail.TABLE_POPUP_STORES,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(DBPopupStoreDetail.COLUMN_NAME))
            val location = cursor.getString(cursor.getColumnIndexOrThrow(DBPopupStoreDetail.COLUMN_LOCATION))
            val period = cursor.getString(cursor.getColumnIndexOrThrow(DBPopupStoreDetail.COLUMN_PERIOD))
            val imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(DBPopupStoreDetail.COLUMN_IMAGE_URL))

            storeName.text = name
            storeLocation.text = location
            storePeriod.text = period
            storeDescription.text = "Store Description" // 여기에 실제 설명을 추가할 수 있습니다.

            Glide.with(this)
                .load(imageUrl)
                .into(storeImage)
        }
        cursor.close()
    }

    private fun getStoreLink(storeId: String): String {
        val selection = "${DBPopupStoreDetail.COLUMN_STORE_ID} = ?"
        val selectionArgs = arrayOf(storeId)
        val cursor = db.query(
            DBPopupStoreDetail.TABLE_POPUP_STORES,
            arrayOf(DBPopupStoreDetail.COLUMN_LINK),
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        var link = ""
        if (cursor.moveToFirst()) {
            link = cursor.getString(cursor.getColumnIndexOrThrow(DBPopupStoreDetail.COLUMN_LINK))
        }
        cursor.close()
        return link
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
        if (storeId != null && isWishListed(storeId!!)) {
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
