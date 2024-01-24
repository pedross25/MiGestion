package com.example.migestion.data.cache

import com.example.migestion.data.db.AlbaranEntity
import com.example.migestion.data.db.Database
import com.example.migestion.data.repositories.albaranrepository.ICacheAlbaran
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbaranCache @Inject constructor(
    private val db: Database
): ICacheAlbaran {
    private val queries = db.albaranQueries

    override suspend fun createAlbaran(idProducts: List<Int>, customer: Int, date: String): AlbaranEntity? {
        return withContext(Dispatchers.IO) {
            var albaranId: Long = -1
            db.transaction {

                db.albaranQueries.insert(customer.toLong(), date)

                albaranId = db.albaranQueries.selectLastInsertedRowId().executeAsOne()

                for (productId in idProducts) {
                    db.albaranProductQueries.insert(albaranId, productId.toLong())
                }

                db.albaranQueries.selectById(albaranId).executeAsOneOrNull()
            }
            db.albaranQueries.selectById(albaranId).executeAsOneOrNull()
        }
    }

    override suspend fun getAllAlbarans(): List<AlbaranEntity> {
        return withContext(Dispatchers.IO) {
            db.albaranQueries.selectAll().executeAsList()
        }
    }


}