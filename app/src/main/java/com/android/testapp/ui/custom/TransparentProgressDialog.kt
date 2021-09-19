package com.android.testapp.ui.custom

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.android.testapp.R

/**
 * Custom dialog class for showing loader.
 */
class TransparentProgressDialog(context: Context) :
    Dialog(context, R.style.TransparentProgressDialog) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.attributes?.gravity = Gravity.CENTER_HORIZONTAL
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)

        val layout = FrameLayout(context)
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val progressBar = ProgressBar(context)
        layout.addView(progressBar, params)
        addContentView(layout, params)
    }
}