package com.example.data

class TestDataApiImpl : TestDataApi {

    override suspend fun getValue() = "network value"
}
