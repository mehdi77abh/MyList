package com.example.mylist.views.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mylist.R
import com.example.mylist.views.main.MainActivity
import kotlinx.coroutines.delay
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Timer().schedule(1000){
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
            this@SplashActivity.finish()
        }



    }
}