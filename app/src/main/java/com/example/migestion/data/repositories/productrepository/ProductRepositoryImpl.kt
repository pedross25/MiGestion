package com.example.migestion.data.repositories.productrepository

import com.example.migestion.data.model.CreateProductParams
import com.example.migestion.data.network.HttpRoutes
import com.example.migestion.data.remote.model.ApiResponse
import com.example.migestion.model.Product
import com.example.migestion.model.Response
import com.example.migestion.model.toProduct
import com.example.migestion.model.toProductEntity
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class ProductResponse(
    val data: List<Product>
)

class ProductRepositoryImpl @Inject constructor(
    private val productDb: ICacheProduct,
    private val httpClient: HttpClient
) : ProductRepository {
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
        product: Product,
        persistApi: Boolean
    ): Response<Product> {
        return try {
            if (persistApi) {
                val message = httpClient.post<ApiResponse<Product>> {
                    url(HttpRoutes.Product.CREATE)
                    contentType(ContentType.Application.Json)
                    body = CreateProductParams(
                        name = product.name,
                        price = product.price,
                        category = product.category,
                        amount = product.amount,
                        description = product.description,
                        template = product.template,
                        invoice = product.invoice
                    )
                }
                Response.Success(message.data)
            } else {
                productDb.insertProduct(product.toProductEntity())
                Response.Success(product)
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    /*
        Usado para persistir en el servidor los productos creados en una factura temporal
     */
    override suspend fun persistProductsFromInvoice(id: Int): Response<List<Product>> {
        return try {
            val products = productDb.getProductsFromInvoice(id).map { it.toProduct() }
            products.forEach {
                addProduct(product = it, persistApi = true)
            }
            Response.Success(data = products)
        } catch (e: Exception) {
            Response.Failure(e)
        }

    }

    // TODO Sacar la peticion a remote
    override suspend fun getTemplateProducts(): Response<List<Product>> {
        return try {
            var products = productDb.getTemplateProducts().map { it.toProduct() }
            if (products.isEmpty()) {
                val message = httpClient.get<ProductResponse> {
                    url(HttpRoutes.Product.GETALL)
                    contentType(ContentType.Application.Json)
                }
                message.data.forEach {
                    productDb.insertProduct(productEntity = it.toProductEntity())
                }
                products = productDb.getTemplateProducts().map { it.toProduct() }
            }
            Response.Success(data = products)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

   /* override suspend fun getProductsFromInvoice(idInvoice: Int): Flow<Response<Product>> {
        return flow {
            try {
                emit(Response.Loading)

                productDb.getFlowProductsByInvoice(idInvoice)

                emit(Response.Success(data = productDb.getFlowProductsByInvoice(idInvoice)))

            } catch (e: Exception) {
                Log.i("GESTION", e.toString())
                emit(Response.Failure(e))
            }
        }
    }*/

    override suspend fun getProductsFromInvoice(idInvoice: Int): Flow<Response<List<Product>>> {
        return productDb.getFlowProductsByInvoice(idInvoice)
            .map { products ->
                if (products.isNotEmpty()) {
                    Response.Success(products.map { it.toProduct() })
                } else {
                    Response.Failure(Exception("No se encontraron productos para la factura $idInvoice"))
                }
            }
            .onStart { emit(Response.Loading) }
            .catch { throwable ->
                emit(Response.Failure(Exception(throwable)))
            }
    }

}