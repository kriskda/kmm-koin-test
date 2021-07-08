package com.example.domain

import com.example.data.TestDataApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TestRepositoryImpl : TestRepository, KoinComponent {

    private val dataApi: TestDataApi by inject()

    override suspend fun getTestValue() = dataApi.getValue()
}
