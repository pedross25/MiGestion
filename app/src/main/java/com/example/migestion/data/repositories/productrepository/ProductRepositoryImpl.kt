package com.example.migestion.data.repositories.productrepository

import com.example.migestion.data.network.HttpRoutes
import com.example.migestion.model.Product
import com.example.migestion.model.Response
import com.example.migestion.model.toProduct
import com.example.migestion.model.toProductEntity
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

data class ProductResponse(
    val data: List<Product>
)
class ProductRepositoryImpl @Inject constructor(
    private val productDb: ICacheProduct,
    private val httpClient: HttpClient
): ProductRepository {
    override suspend fun getAllProducts(): Response<List<Product>> {
        return try {
            val products = productDb.getAllProducts()
            if (products.isNotEmpty()) {
                Response.Success(products.map { it.toProduct() })
            } else {
                val message = httpClient.get<ProductResponse> {
                    url(HttpRoutes.Product.GETALL)
                    contentType(ContentType.Application.Json)
                }
                message.data.forEach {
                    productDb.insertProduct(productEntity = it.toProductEntity())
                }
                Response.Success(data = message.data)
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addProduct(
        name: String,
        description: String,
        imageUrl: String,
        price: Double
    ): Product {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(id: Int): Product {
        TODO("Not yet implemented")
    }

}