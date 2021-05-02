package com.picpay.desafio.android.data.source

import com.picpay.desafio.android.domain.remote.ContactsResponseFake
import kotlinx.coroutines.flow.flow


class ContactDataSourceFake {

    suspend fun fetchUsersSuccess() = flow {
        val contacts = listOf(
            ContactsResponseFake(
                image = "www.google.com",
                name = "Evandro",
                id = 0,
                username = "@evandro.costa"
            )
        )
        emit(contacts)
    }

    suspend fun fetchUsersWithFieldsNull() = flow {
        val list = mutableListOf<ContactsResponseFake>()
        list.add(
            ContactsResponseFake(
                image = null,
                name = "Evandro",
                id = 2,
                username = null
            )
        )
        emit(list)
    }

    suspend fun fetchUsersWithError() = flow {
        val list: List<ContactsResponseFake>? = null
        throw Exception("Falha ao buscar lista de contados")
        emit(list!!)
    }
}