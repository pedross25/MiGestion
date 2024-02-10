package com.example.migestion.data.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.observer.ResponseObserver

import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Inject

/*
class MiGestionHttpClient @Inject constructor() {

    fun getHttpClient() = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json{
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                //explicitNulls = false
            })
        }

        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    Log.i(TAG_KTOR_LOGGER, message)
                }
            }
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.i(TAG_HTTP_STATUS_LOGGER, "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }
    }

    companion object {
        private const val TIME_OUT = 10_000
        private const val TAG_KTOR_LOGGER = "ktor_logger:"
        private const val TAG_HTTP_STATUS_LOGGER = "http_status:"
    }
}*/

class MiGestionHttpClient @Inject constructor() {
    fun getHttpClient() = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }

    }

}
