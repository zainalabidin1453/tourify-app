package com.tourify.tourifyapp.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

object Toasty {
    fun show(
        context: Context,
        message: Int,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        val myToast = Toast.makeText(context, message, duration)
        myToast.setGravity(Gravity.CENTER, 0, 35)
        myToast.show()
    }
}

