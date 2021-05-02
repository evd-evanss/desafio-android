package com.picpay.desafio.android.domain.mapper

import com.picpay.desafio.android.domain.local.UserContactFake
import com.picpay.desafio.android.domain.remote.ContactsResponseFake
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class ContactsMapperFake @Inject constructor() {

    fun responseToModel(response: Flow<List<ContactsResponseFake>>) = flow {
        response.single().run {
            val userList = mutableListOf<UserContactFake>()
            forEach {
                userList.add(
                    UserContactFake (
                        image = it.image ?: "",
                        name = it.name ?: "",
                        id = it.id ?: 0,
                        username = it.username ?: ""
                    )
                )
            }
            emit(userList)
        }
    }
}