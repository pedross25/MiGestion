package com.example.migestion.data.repositories.albaranrepository

import android.util.Log
import com.example.migestion.data.db.AlbaranEntity
import com.example.migestion.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlbaranRepositoryImpl @Inject constructor(
    private val albaranDb: ICacheAlbaran,
    private val albaranApi: IRemoteAlbaran
) : AlbaranRepository {

    /*override suspend fun createAlbaran(idProducts: List<Int>, customer: Int): Flow<Response<Any>> {
        return flow {
            try {
                emit(Response.Loading)
                val message = httpClient.post<HttpResponse> { // or your data class
                    url("http://10.0.2.2:8080/albaran/create")
                    contentType(ContentType.Application.Json)
                    body = AlbaranData(
                        idCustomer = customer,
                        products = idProducts
                    )
                }
                if (message.status == HttpStatusCode.OK) {
                    emit(Response.Success(message.content))
                } else {
                    emit(Response.Failure(Exception(message.content.toString())))
                    Log.i("GESTION", message.toString())
                }

            } catch (e: Exception) {
                Log.i("GESTION", e.toString())
                emit(Response.Failure(e))
            }
        }
    }

    override suspend fun getAllAlbarans(): Flow<Response<Any>> {
        return flow {
            try {
                emit(Response.Loading)
                val message = httpClient.get<InvoiceResponse> {
                    url("http://10.0.2.2:8080/albaran/getAll")
                    contentType(ContentType.Application.Json)
                }
                emit(Response.Success(data = message))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }
    }*/
    override suspend fun createAlbaran(
        idProducts: List<Int>,
        customer: Int
    ): Flow<Response<AlbaranEntity>> {
        return flow {
            try {
                emit(Response.Loading)

                /*val albaran = customerApi.createCustomer(name = name,
                    streetAddress = streetAddress,
                    city = city,
                    state = state,
                    postalCode = postalCode,
                    email = email,
                    phoneNumber = phoneNumber,
                    cif = cif)

                if (customer != null) {
                    withContext(Dispatchers.IO) {
                        customerDb.insertCustomer(customer)
                    }
                    emit(Response.Success(customer))
                }*/
            } catch (e: Exception) {
                Log.i("GESTION", e.toString())
                emit(Response.Failure(e))
            }
        }
        TODO("Not yet implemented")
    }

    override suspend fun getAllAlbarans(): Flow<Response<List<AlbaranEntity>>> {
        TODO("Not yet implemented")
    }

}