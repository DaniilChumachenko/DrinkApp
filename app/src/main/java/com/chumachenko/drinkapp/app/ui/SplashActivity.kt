package com.chumachenko.drinkapp.app.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.chumachenko.drinkapp.R
import com.chumachenko.drinkapp.databinding.ActivityMainBinding
import com.chumachenko.drinkapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private val uiHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        uiHandler.postDelayed({
            startActivity(AppActivity.newIntent(this))
            finish()
        }, 500)
    }

    override fun onDestroy() {
        uiHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}