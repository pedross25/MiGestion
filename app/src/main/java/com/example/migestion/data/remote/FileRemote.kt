package com.example.migestion.data.remote

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.example.migestion.data.network.HttpRoutes
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import io.ktor.http.content.PartData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.File
import javax.inject.Inject

class FileRemote @Inject constructor(
    private val httpClient: HttpClient,
) {


    suspend fun getBitmap(uri: Uri): Bitmap? {
        return runCatching {
            val byteArray = withContext(Dispatchers.IO) {
                httpClient.get<ByteArray>(Url(uri.toString()))
            }

            return withContext(Dispatchers.Default) {
                ByteArrayInputStream(byteArray).use {
                    val option = BitmapFactory.Options()
                    option.inPreferredConfig =
                        Bitmap.Config.RGB_565 // To save memory, or use RGB_8888 if alpha channel is expected
                    BitmapFactory.decodeStream(it, null, option)
                }
            }
        }.getOrElse {
            Log.e("getBitmap", "Failed to get bitmap ${it.message ?: ""}")
            null
        }
    }

    suspend fun uploadImage1(image: File) {
        try {
            val message = httpClient.post<HttpClient> {
                url(HttpRoutes.Image.UPLOAD)
                body = MultiPartFormDataContent(formData {
                    append("image", image.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, ContentType.Image.Any.toString())
                        append(HttpHeaders.ContentDisposition, "filename=${image.name}")
                    })
                })
            }
            //message.data.map()
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    suspend fun uploadImage(image: File) {
        try {
            val response: HttpResponse = httpClient.submitFormWithBinaryData(
                url = "http://localhost:8080/upload",
                formData = formData {
                    append("description", "Ktor logo")
                    append("image", image.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=\"ktor_logo.png\"")
                    })
                }
            )
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    suspend fun uploadImage2(image: File) {
        try {
            val response: HttpResponse = httpClient.post("http://localhost:8080/upload") {
                body = (MultiPartFormDataContent(
                    formData {
                        append("description", "Ktor logo")
                        append("image", image.readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, "image/png")
                            append(HttpHeaders.ContentDisposition, "filename=\"ktor_logo.png\"")
                        })
                    },
                    /*boundary = "WebAppBoundary"*/
                )
                )

            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }





}