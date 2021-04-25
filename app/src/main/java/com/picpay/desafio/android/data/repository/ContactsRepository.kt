package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.source.ContactsDataSource
import com.picpay.desafio.android.domain.model.local.UserContact
import com.picpay.desafio.android.domain.model.mapper.ContactsMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ContactsRepository {
    fun fetchContacts() : Flow<List<UserContact>>
}

class ContactsRepositoryImpl @Inject constructor(
    private val contactsDataSource: ContactsDataSource,
    private val mapper: ContactsMapper
) : ContactsRepository {

    override fun fetchContacts() = mapper.responseToModel(contactsDataSource.fetchContacts())
}
