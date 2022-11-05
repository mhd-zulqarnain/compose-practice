package com.project.tailor.di

import com.project.tailor.api.AuthApi
import com.project.tailor.api.BASE_URL
import com.project.tailor.api.FlexApi
import com.project.tailor.di.qualifiers.ApiGateway
import com.project.tailor.di.qualifiers.Authentication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreNetworkModule {

    @Provides
    @Singleton
    @ApiGateway
    fun retrofitGateway(
        @ApiGateway client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(BASE_URL).build()


    @Provides
    @Singleton
    fun flexApi(@ApiGateway retrofit: Retrofit): FlexApi =
        retrofit.create(FlexApi::class.java)

    @Provides
    @Singleton
    @Authentication
    fun retrofitAuthGateway(
        @Authentication client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(BASE_URL).build()


    @Provides
    @Singleton
    fun authApi(@Authentication retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)
}
