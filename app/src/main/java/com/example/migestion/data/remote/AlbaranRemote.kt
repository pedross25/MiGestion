package com.example.migestion.data.remote

import com.example.migestion.data.db.AlbaranEntity
import com.example.migestion.data.repositories.albaranrepository.IRemoteAlbaran
import io.ktor.client.HttpClient
import javax.inject.Inject

class AlbaranRemote @Inject constructor(
    private val httpClient: HttpClient,
) : IRemoteAlbaran {
    override suspend fun createAlbaran(
        idProducts: List<Int>,
        customer: Int,
        date: String
    ): AlbaranEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<AlbaranEntity> {
        TODO("Not yet implemented")

    }
}