package com.picpay.desafio.android._di.modules

import android.content.Context
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.utils.Constants.CACHE_HEADER
import com.picpay.desafio.android.utils.Constants.FIVE_MEGA
import com.picpay.desafio.android.utils.Constants.ONE_MINUTE
import com.picpay.desafio.android.utils.Constants.ONE_WEEK
import com.picpay.desafio.android.utils.extensions.hasNetwork
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun retrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun providesClient(@ApplicationContext context: Context) = OkHttpClient.Builder()
        .cache(Cache(context.cacheDir, (FIVE_MEGA).toLong()))
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (context.hasNetwork())
                request.newBuilder().header(CACHE_HEADER, ONE_MINUTE).build()
            else
                request.newBuilder().header(CACHE_HEADER, ONE_WEEK).build()
            chain.proceed(request)
        }
        .addInterceptor(ChuckInterceptor(context))
        .build()
}