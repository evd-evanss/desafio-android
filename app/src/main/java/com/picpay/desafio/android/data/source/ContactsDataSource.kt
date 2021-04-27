package com.picpay.desafio.android.data.source

import com.picpay.desafio.android.domain.model.remote.ContactsResponse
import com.picpay.desafio.android.utils.factory.RetrofitServiceFactory
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

class ContactsDataSource @Inject constructor(
    retrofit: Retrofit.Builder,
    okHttpClient: OkHttpClient
) {
    private val service = RetrofitServiceFactory(retrofit, okHttpClient)
        .newInstance<PicPayService>()

    fun fetchContacts() = flow {
        emit(service.fetContacts())
    }

    interface PicPayService {

        @GET("users")
        suspend fun fetContacts(): List<ContactsResponse>
    }
}