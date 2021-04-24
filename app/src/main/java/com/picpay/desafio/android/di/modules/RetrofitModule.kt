package com.picpay.desafio.android.di.modules

import android.content.Context
import com.picpay.desafio.android.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun retrofit(@ApplicationContext context: Context): Retrofit.Builder {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(ChuckInterceptor(context))
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }
}