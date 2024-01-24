package com.example.migestion.data.repositories.productrepository

import com.example.migestion.model.Product
import com.example.migestion.model.Response

typealias Products = List<Product>
typealias ProductsResponse = Response<Products>
//typealias ProductResponse = Response<Product>
typealias AddProductResponse = Response<Boolean>
typealias DeleteProductResponse = Response<Boolean>

interface ProductRepository {

    suspend fun getAllProducts(): Response<List<Product>>

    suspend fun addProduct(
        name: String,
        description: String,
        imageUrl: String,
        price: Double
    ): Product

    //suspend fun deleteProductFromFirestore(productId: String): DeleteProductResponse

    suspend fun getProductById(id: Int): Product

    //suspend fun addNewWinBid(productId: String, idBid: String): Response<Boolean>
}