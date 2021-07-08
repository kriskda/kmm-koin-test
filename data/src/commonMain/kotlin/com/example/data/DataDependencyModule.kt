package com.example.data

import org.koin.dsl.module

val dataDependencyModule = module {
    factory<TestDataApi> { TestDataApiImpl() }
}
