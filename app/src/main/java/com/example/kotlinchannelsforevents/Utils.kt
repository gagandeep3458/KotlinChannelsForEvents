package com.example.kotlinchannelsforevents

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(view: View, message: String,duration: Int = Snackbar.LENGTH_SHORT, anchor: View? = null) {
    Snackbar.make(view, message, duration).also {
        if (anchor!= null) it.anchorView = anchor
    }.show()
}