package com.project.tailor.di

import android.content.Context
import com.project.tailor.data.storage.EncryptedSharedPreferencesStorage
import com.project.tailor.data.storage.PersistenceSharedPreferencesStorage
import com.project.tailor.data.storage.PersistenceStorage
import com.project.tailor.di.qualifiers.EncryptedStorage
import com.google.gson.Gson
import com.project.tailor.storage.Storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// Tells Dagger this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    @EncryptedStorage
    fun encryptedPreferenceStorage(@ApplicationContext context: Context): Storage =
        EncryptedSharedPreferencesStorage(context)

    @Provides
    @Singleton
    fun persistencePreferenceStorage(
        @ApplicationContext
        context: Context,
        gson: Gson,
        @EncryptedStorage storage: Storage
    ): PersistenceStorage = PersistenceSharedPreferencesStorage(context, gson, storage)
}
