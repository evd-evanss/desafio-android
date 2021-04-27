package com.picpay.desafio.android.data.source

import com.picpay.desafio.android.domain.model.local.UserContact
import kotlinx.coroutines.flow.flow

class FakeService {

    suspend fun fetchUsersSuccess() = flow {
        val contacts = listOf(
            UserContact(
                image = "www.google.com",
                name = "Evandro",
                id = 0,
                username = "@evandro.costa"
            )
        )
        emit(contacts)
    }

    suspend fun fetchUsersError() = flow {
        val list: List<UserContact>? = null
        emit(list!!)
    }
}