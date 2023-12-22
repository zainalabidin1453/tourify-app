package com.tourify.tourifyapp.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

object Toasty {
    fun show(
        context: Context,
        message: Int,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        val myToast = Toast.makeText(context, message, duration)
        myToast.setGravity(Gravity.TOP, 0, 35)
        myToast.show()
    }
    fun show2(
        context: Context,
        message: String,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        val myToast = Toast.makeText(context, cutMessage(message), duration)
        myToast.setGravity(Gravity.TOP, 0, 35)
        myToast.show()
    }
}

