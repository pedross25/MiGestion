package com.example.migestion.data.repositories.productrepository

import com.example.migestion.data.model.CreateProductParams
import com.example.migestion.data.network.HttpRoutes
import com.example.migestion.data.remote.model.ApiResponse
import com.example.migestion.model.Product
import com.example.migestion.model.Response
import com.example.migestion.model.toProduct
import com.example.migestion.model.toProductEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


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
                val message = httpClient.get(HttpRoutes.Product.GETALL) {
                    contentType(ContentType.Application.Json)
                }
                message.body<List<Product>>().forEach {
                    productDb.insertProduct(productEntity = it.toProductEntity())
                }
                Response.Success(data = message.body())
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addProduct(
        product: Product,
        persistApi: Boolean
    ): Response<Product> {
        return addProduct(
            product.name,
            product.description,
            product.price,
            product.amount,
            product.category,
            product.template,
            product.invoice,
            product.parentId,
            persistApi
        )
    }

    override suspend fun addProduct(
        name: String,
        description: String?,
        price: Double,
        quantity: Int,
        category: String,
        template: Boolean,
        invoice: Int?,
        parentId: Int?,
        persistApi: Boolean
    ): Response<Product> {
        return try {
            if (persistApi) {
                val message = httpClient.post(HttpRoutes.Product.CREATE) {
                    contentType(ContentType.Application.Json)
                    setBody(
                        CreateProductParams(
                            name = name,
                            price = price,
                            category = category,
                            amount = quantity,
                            description = description,
                            template = template,
                            parentId = parentId,
                            invoice = invoice
                        )
                    )
                }
                val product = message.body<ApiResponse<Product>>().data
                productDb.insertProduct(
                    product.toProductEntity()
                )
                Response.Success(product)
            } else {
                val product = Product(
                    productDb.getNextId(),
                    name = name,
                    amount = quantity,
                    price = price,
                    category = category,
                    template = template,
                    description = description,
                    parentId = parentId,
                    invoice = invoice,
                )
                productDb.insertProduct(
                    product.toProductEntity()
                )
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
    override suspend fun getTemplateProducts(): List<Product> {
        return try {
            var products = productDb.getTemplateProducts().map { it.toProduct() }
            if (products.isEmpty()) {
                val message = httpClient.get(HttpRoutes.Product.GETALL) {
                    contentType(ContentType.Application.Json)
                }
                message.body<ApiResponse<List<Product>>>().data.forEach {
                    productDb.insertProduct(productEntity = it.toProductEntity())
                }
                products = productDb.getTemplateProducts().map { it.toProduct() }
            }
            return products
        } catch (e: Exception) {
            return emptyList()
        }
    }

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