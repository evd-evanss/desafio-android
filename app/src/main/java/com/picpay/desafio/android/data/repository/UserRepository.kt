package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.source.UserDataSource
import com.picpay.desafio.android.domain.model.local.User
import com.picpay.desafio.android.domain.model.mapper.UserMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
    fun fetchUsers() : Flow<List<User>>
}

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val mapper: UserMapper
) : UserRepository {

    override fun fetchUsers() = mapper.responseToModel(userDataSource.fetchUsers())
}
