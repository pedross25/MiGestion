package com.example.migestion.data.repositories.albaranrepository

import com.example.migestion.data.db.AlbaranEntity


interface ICacheAlbaran {
    suspend fun createAlbaran(
        idProducts: List<Int>,
        customer: Int,
        date: String
    ): AlbaranEntity?

    suspend fun getAllAlbarans(): List<AlbaranEntity>
}