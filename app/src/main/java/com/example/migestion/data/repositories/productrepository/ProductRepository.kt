package com.example.migestion.data.repositories.productrepository

import com.example.migestion.model.Product
import com.example.migestion.model.Response
import kotlinx.coroutines.flow.Flow

typealias Products = List<Product>
typealias ProductsResponse = Response<Products>
//typealias ProductResponse = Response<Product>
typealias AddProductResponse = Response<Boolean>
typealias DeleteProductResponse = Response<Boolean>

interface ProductRepository {

    suspend fun getAllProducts(): Response<List<Product>>

    suspend fun addProduct(
        product: Product, persistApi: Boolean = false
    ): Response<Product>

    //suspend fun deleteProductFromFirestore(productId: String): DeleteProductResponse

    suspend fun persistProductsFromInvoice(id: Int): Response<List<Product>>

    suspend fun getTemplateProducts(): Response<List<Product>>

    suspend fun getProductsFromInvoice(idInvoice: Int): Flow<Response<List<Product>>>


    //suspend fun addNewWinBid(productId: String, idBid: String): Response<Boolean>
}