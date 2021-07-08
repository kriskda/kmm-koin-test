package com.example.domain

import com.example.domain.TestRepository
import com.example.domain.TestUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TestUseCaseImpl : TestUseCase, KoinComponent {

    private val repository: TestRepository by inject()

    @Throws(Exception::class)
    override suspend operator fun invoke() =
        repository.getTestValue()
}
