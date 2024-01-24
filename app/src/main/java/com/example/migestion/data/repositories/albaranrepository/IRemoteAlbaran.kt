package com.example.migestion.data.repositories.albaranrepository

import com.example.migestion.data.db.AlbaranEntity


interface IRemoteAlbaran {
    suspend fun createAlbaran(
        idProducts: List<Int>, customer: Int, date: String
    ): AlbaranEntity?

    suspend fun getAll(): List<AlbaranEntity>
}