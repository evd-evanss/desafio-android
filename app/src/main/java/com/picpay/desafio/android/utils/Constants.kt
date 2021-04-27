package com.picpay.desafio.android.utils

object Constants {

    private const val WEEK_IN_SECONDS = 60 * 60 * 24 * 7
    const val FIVE_MEGA = 5 * 1024 * 1024
    const val ONE_MINUTE = "public, max-age= 60"
    const val ONE_WEEK = "public, only-if-cached, max-stale=$WEEK_IN_SECONDS"
    const val CACHE_HEADER = "Cache-Control"
}