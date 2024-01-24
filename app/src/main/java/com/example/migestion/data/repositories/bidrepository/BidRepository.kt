package com.example.migestion.data.repositories.bidrepository

import com.example.migestion.model.Response


typealias AddBidResponse = Response<String>

interface BidRepository {

    suspend fun addBid(
        product: String? = null,
        user: String,
        amount: Double
    ): Response<String>

}