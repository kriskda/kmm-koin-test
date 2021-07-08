package com.example.domain

interface TestUseCase {

    @Throws(Exception::class)
    suspend operator fun invoke(): String
}
