package com.example.domain

interface TestRepository {

    suspend fun getTestValue() : String
}
