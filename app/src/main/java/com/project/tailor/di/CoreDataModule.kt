/*
 * Copyright 2018 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.project.tailor.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.project.tailor.api.AccessTokenInterceptor
import com.project.tailor.api.MockInterceptor
import com.project.tailor.di.qualifiers.ApiGateway
import com.project.tailor.di.qualifiers.Authentication
import com.project.tailor.utils.SealedClassTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
//import com.project.tailor.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.jvm.internal.Reflection

/**
 * Dagger module to provide core data functionality.
 */
const val timeout: Long = 40

@Module
@InstallIn(SingletonComponent::class)
class CoreDataModule {

    @Provides
    @Singleton
    @ApiGateway
    fun okHttpClientApiGateway(
        loggingInterceptor: HttpLoggingInterceptor,
        mockInterceptor: Interceptor,
        chuckerInterceptor: ChuckerInterceptor,
        accessTokenInterceptor: AccessTokenInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
//        if (BuildConfig.DEBUG)
            builder.addInterceptor(chuckerInterceptor)
        builder
            .addInterceptor(loggingInterceptor)
            .addInterceptor(accessTokenInterceptor)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
//        if (BuildConfig.FLAVOR == "dev" && BuildConfig.BUILD_TYPE == "staging")
            builder.addInterceptor(mockInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    @Authentication
    fun okHttpClientAuthApiGateway(
        loggingInterceptor: HttpLoggingInterceptor,
        mockInterceptor: Interceptor,
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
//        if (BuildConfig.DEBUG)
        builder.addInterceptor(chuckerInterceptor)
        builder
            .addInterceptor(loggingInterceptor)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
//        if (BuildConfig.FLAVOR == "dev" && BuildConfig.BUILD_TYPE == "staging")
            builder.addInterceptor(mockInterceptor)
        return builder.build()
    }


    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
//            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
//            } else {
//                HttpLoggingInterceptor.Level.NONE
//            }
        }

    @Provides
    @Singleton
    fun chuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector
    ): ChuckerInterceptor {
        return ChuckerInterceptor(
            context, // List of headers to replace with ** in the Chucker UI
            collector = chuckerCollector,
            // Read the whole response body even when the client does not consume the response completely.
            // This is useful in case of parsing errors or when the response body
            // is closed before being read like in Retrofit with Void and Unit types.
            alwaysReadResponseBody = true
        )
    }

    @Provides
    @Singleton
    fun chuckerCollector(@ApplicationContext context: Context) = ChuckerCollector(
        context = context,
        // Allows to customize the retention period of collected data
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )


    @Provides
    @Singleton
    fun provideMockInterceptor(): Interceptor = MockInterceptor()

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().registerTypeAdapterFactory(
        object : TypeAdapterFactory {
            override fun <T : Any> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {
                val kclass = Reflection.getOrCreateKotlinClass(type.rawType)
                return if (kclass.sealedSubclasses.any()) {
                    SealedClassTypeAdapter(kclass, gson)
                } else
                    gson.getDelegateAdapter(this, type)
            }

        }).setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    @Provides
    @Singleton
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)
}
