package com.jj.kurly.core.network.di

import android.content.Context
import com.jj.kurly.core.network.NetworkDataSource
import com.jj.kurly.core.network.retrofit.RetrofitNetwork
import com.kurly.android.mockserver.MockInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    internal abstract fun bindsNetworkDataSource(
        networkDataSource: RetrofitNetwork
    ): NetworkDataSource

    companion object {
        @Provides
        @Singleton
        fun providesOkHttpClient(
            @ApplicationContext context: Context
        ): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(MockInterceptor(context))
                .build()
    }
}