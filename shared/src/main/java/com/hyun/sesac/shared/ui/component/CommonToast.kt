package com.hyun.sesac.shared.ui.component

import android.content.Context
import android.widget.Toast

fun commonToast(context: Context, message: String = "", delayTime: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, delayTime).show()
}