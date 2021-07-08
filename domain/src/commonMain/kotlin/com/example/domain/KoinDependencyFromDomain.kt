package com.example.domain

import com.example.data.dataDependencyModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            dataDependencyModule,
            domainDependencyModule
        )
    }

fun initKoin() = initKoin {}
