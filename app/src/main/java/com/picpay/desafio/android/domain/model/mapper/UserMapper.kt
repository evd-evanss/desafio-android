package com.picpay.desafio.android.domain.model.mapper

import com.picpay.desafio.android.domain.model.local.User
import com.picpay.desafio.android.domain.model.remote.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun responseToModel(response: Flow<List<UserResponse>>) = flow {
        response.single().run {
            val userList = mutableListOf<User>()
            forEach {
                userList.add(
                    User (
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