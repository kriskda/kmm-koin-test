package com.example.kmm_koin_test

import com.example.data.dataDependencyModule
import com.example.domain.domainDependencyModule
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


