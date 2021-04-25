package com.picpay.desafio.android

import com.picpay.desafio.android.domain.model.local.UserContact

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<UserContact> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}