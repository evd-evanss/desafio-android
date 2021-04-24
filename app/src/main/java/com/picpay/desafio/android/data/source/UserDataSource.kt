package com.picpay.desafio.android.data.source

import com.picpay.desafio.android.domain.model.remote.UserResponse
import com.picpay.desafio.android.utils.factory.RetrofitServiceFactory
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val retrofit: Retrofit.Builder
) {
    private val service = RetrofitServiceFactory(retrofit)
        .newInstance<UserService>()

    fun fetchUsers() = flow {
        emit(service.fetchUsers())
    }

    interface UserService {

        @GET("users")
        suspend fun fetchUsers(): List<UserResponse>
    }
}