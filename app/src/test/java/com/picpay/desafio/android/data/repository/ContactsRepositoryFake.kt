package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.source.ContactDataSourceFake
import com.picpay.desafio.android.domain.local.UserContactFake
import com.picpay.desafio.android.domain.mapper.ContactsMapperFake
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import javax.inject.Inject

interface ContactsRepositoryFake {
    fun fetchContactsSuccess(): Flow<List<UserContactFake>>
    fun fetchContactsSuccessWithNullSafe(): Flow<List<UserContactFake>>
    fun fetchContactsError(): Flow<List<UserContactFake>>
}

class ContactsRepositoryImplFake @Inject constructor(
    private val dataSource: ContactDataSourceFake,
    private val mapper: ContactsMapperFake
) : ContactsRepositoryFake {

    override fun fetchContactsSuccess() = flow {
        emit(
            mapper.responseToModel(
                dataSource.fetchUsersSuccess()
            ).single()
        )
    }

    override fun fetchContactsSuccessWithNullSafe() = flow {
        emit(
            mapper.responseToModel(
                dataSource.fetchUsersWithFieldsNull()
            ).single()
        )
    }

    override fun fetchContactsError() = flow {
        emit(
            mapper.responseToModel(
                dataSource.fetchUsersWithError()
            ).single()
        )
    }
}