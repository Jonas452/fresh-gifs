package com.jonas.freshgifs.di

import javax.inject.Qualifier

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class IODispatcher

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class MainDispatcher
