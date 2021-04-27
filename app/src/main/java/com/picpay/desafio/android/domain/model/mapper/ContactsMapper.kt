package com.picpay.desafio.android.domain.model.mapper

import com.picpay.desafio.android.domain.model.local.UserContact
import com.picpay.desafio.android.domain.model.remote.ContactsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class ContactsMapper @Inject constructor() {

    fun responseToModel(response: Flow<List<ContactsResponse>>) = flow {
        response.single().run {
            val userList = mutableListOf<UserContact>()
            forEach {
                userList.add(
                    UserContact (
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