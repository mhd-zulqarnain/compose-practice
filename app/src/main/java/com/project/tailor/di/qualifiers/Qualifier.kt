package com.project.tailor.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiGateway

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Authentication

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EncryptedStorage

