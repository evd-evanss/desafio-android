package com.picpay.desafio.android.utils.extensions

import android.view.View

fun View.setVisible(
    visible: Boolean,
    useInvisible: Boolean = false,
) {
    visibility = when {
        visible -> View.VISIBLE
        useInvisible -> View.INVISIBLE
        else -> View.GONE
    }
}