package com.example.domain

import org.koin.dsl.module

val domainDependencyModule = module {
    factory<TestUseCase> { TestUseCaseImpl() }
    factory<TestRepository> { TestRepositoryImpl() }
}
