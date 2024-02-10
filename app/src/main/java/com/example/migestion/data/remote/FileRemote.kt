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
import io.ktor.client.request.setBody
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

    suspend fun uploadImage2(image: File) {
        try {
            val response: HttpResponse = httpClient.post(HttpRoutes.Image.UPLOAD) {
                setBody(MultiPartFormDataContent(
                    formData {
                        append("description", "Ktor logo")
                        append("image", image.readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, "image/png")
                            append(HttpHeaders.ContentDisposition, "filename=\"${image.name}\"")
                        })
                    },
                    boundary = "WebAppBoundary"
                )
                )

            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }





}