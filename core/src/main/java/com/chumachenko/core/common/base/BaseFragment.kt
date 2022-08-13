package com.chumachenko.core.common.base

import android.app.Activity
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chumachenko.core.R
import com.chumachenko.core.common.InsetHolder
import com.chumachenko.core.common.NetworkUtils
import com.chumachenko.core.common.Router
import com.chumachenko.core.extensions.dpToPixel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject


abstract class BaseFragment(@LayoutRes val layoutRes: Int) : Fragment(layoutRes) {

    private val networkUtils: NetworkUtils by inject()

    protected val router: Router by lazy {
        requireActivity().application as Router
    }

    private var insetHolder: InsetHolder? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            insetHolder?.onKeyboardEvent(imeVisible, imeHeight)
            insets
        }
    }

    protected fun replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment
    ): FragmentTransaction? {
        activity?.let {
            val transaction = it.supportFragmentManager
                .beginTransaction()
                .replace(containerViewId, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName)

            if (!it.supportFragmentManager.isStateSaved) {
                return transaction
            }
        }
        return null
    }

    protected fun addFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment
    ): FragmentTransaction? {
        activity?.let {
            val transaction = it.supportFragmentManager
                .beginTransaction()
                .add(containerViewId, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName)

            if (!it.supportFragmentManager.isStateSaved) {
                return transaction
            }
        }
        return null
    }

    protected fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    protected fun showKeyboard(view: View) {
        val inputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.showSoftInput(view, 0)
    }

    protected fun isOnline(): Boolean {
        return networkUtils.isOnline()
    }

    protected fun getBottomSheetPeekHeight(toolbarHeightDp: Int): Int {
        val display = requireActivity().windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)

        val statusHeight = (requireActivity() as InsetHolder).topInset
        val toolbarHeight = dpToPixel(toolbarHeightDp, requireContext())
        val peekHeight = point.y - statusHeight - toolbarHeight
        return peekHeight.toInt()
    }

    protected fun requestAdjustResize() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    protected fun removeAdjustResize() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    fun drinksSnackbar(view: View) {
        hideKeyboard(view)
        val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
        val customSnackView: View =
            layoutInflater.inflate(R.layout.drinks_snackbar, null)

        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

        snackbarLayout.setPadding(0, 0, 0, 0)
        snackbarLayout.addView(customSnackView, 0)
        snackbar.show()

    }
}

inline fun arguments(block: Bundle.() -> Unit): Bundle {
    val bundle = Bundle()
    block(bundle)
    return bundle
}
