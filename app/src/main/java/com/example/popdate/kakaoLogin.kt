package com.example.popdate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient

class kakaoLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }

    private fun login() {
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null) {
                Log.e("KakaoLogin", "로그인 실패", error)
            } else if (token != null) {
                Log.i("KakaoLogin", "로그인 성공 ${token.accessToken}")
            }
        }
    }

}