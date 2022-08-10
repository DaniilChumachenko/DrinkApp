package com.chumachenko.drinkapp.app.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.chumachenko.core.common.InsetHolder
import com.chumachenko.core.extensions.dpToPixel
import com.chumachenko.core.extensions.viewBinding
import com.chumachenko.core.ui.CoreFragment
import com.chumachenko.drinkapp.R
import com.chumachenko.drinkapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppActivity : AppCompatActivity(), InsetHolder {

    override val topInset: Int
        get() = _insetTop
    override val bottomInset: Int
        get() = _insetBottom

    private var _insetTop = 0
    private var _insetBottom = 0
    private var isWindowInsetSet = false

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<AppViewModel>()

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(INSET_TOP, _insetTop)
        outState.putInt(INSET_BOTTOM, _insetBottom)
        outState.putBoolean(IS_WINDOW_INSET_SET, isWindowInsetSet)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(viewModel)

        savedInstanceState?.apply {
            _insetTop = getInt(INSET_TOP)
            _insetBottom = getInt(INSET_BOTTOM)
            isWindowInsetSet = getBoolean(IS_WINDOW_INSET_SET)
        }

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            WindowCompat.setDecorFitsSystemWindows(this, false)

            val insetsController = WindowInsetsControllerCompat(this, binding.root)
            insetsController.isAppearanceLightStatusBars = true
            statusBarColor = Color.TRANSPARENT
        }

        if (isWindowInsetSet) {
            if (savedInstanceState == null) startCoreFragment()
        } else {
            binding.root.setOnApplyWindowInsetsListener { _, insets ->
                val insetsCompat = WindowInsetsCompat.toWindowInsetsCompat(insets)
                    .getInsets(WindowInsetsCompat.Type.systemGestures())

                if (!isWindowInsetSet) {
                    _insetTop = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val displayCutout = insets.displayCutout
                        displayCutout?.safeInsetTop ?: insetsCompat.top
                    } else {
                        dpToPixel(24, this).toInt()
                    }

                    _insetBottom = WindowInsetsCompat.toWindowInsetsCompat(insets)
                        .getInsets(WindowInsetsCompat.Type.systemBars()).bottom

                    if (_insetBottom == 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && insetsCompat.bottom != 0) {
                        _insetBottom = insetsCompat.bottom
                    }

                    isWindowInsetSet = true

                    val params = binding.root.layoutParams as FrameLayout.LayoutParams
                    params.bottomMargin = _insetBottom
                    binding.root.layoutParams = params

                    if (savedInstanceState == null) startCoreFragment()
                }

                insets
            }
        }
        startCoreFragment()
    }

    private fun startCoreFragment() {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.container, CoreFragment.newInstance())
        if (!supportFragmentManager.isStateSaved) {
            transaction.commit()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
    }

    companion object {
        private const val INSET_TOP = "insetTop"
        private const val INSET_BOTTOM = "insetBottom"
        private const val IS_WINDOW_INSET_SET = "isWindowInsetSet"

        fun newIntent(context: Context): Intent {
            return Intent(context, AppActivity::class.java)
        }
    }
}
