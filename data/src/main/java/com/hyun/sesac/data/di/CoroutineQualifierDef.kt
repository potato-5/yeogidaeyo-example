package com.hyun.sesac.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY) // Byte 코드에는 남아있지만 Runtime에는 사라지는 상태
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher