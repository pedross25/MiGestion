package com.example.migestion.data.repositories.albaranrepository

import com.example.migestion.data.db.AlbaranEntity
import com.example.migestion.model.Response
import kotlinx.coroutines.flow.Flow

interface AlbaranRepository {

    suspend fun createAlbaran(
        idProducts: List<Int>,
        customer: Int,
    ): Flow<Response<AlbaranEntity>>

    suspend fun getAllAlbarans(): Flow<Response<List<AlbaranEntity>>>
}