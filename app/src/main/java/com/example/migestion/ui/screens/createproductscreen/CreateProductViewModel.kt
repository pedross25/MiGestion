package com.example.migestion.ui.screens.createproductscreen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.remote.FileRemote
import com.example.migestion.data.repositories.productrepository.ProductRepository
import com.example.migestion.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ApplicationContext val context: Context,
    private val productRepository: ProductRepository,
    private val fileRepository: FileRemote
) : ViewModel() {

    val productName = mutableStateOf("")
    val productDescription = mutableStateOf("")
    val productQuantity = mutableStateOf("")
    val productPrice = mutableStateOf("")
    val productPhotos = mutableStateOf<List<Uri>>(emptyList())

    fun setProductName(name: String) {
        productName.value = name
    }

    fun setProductDescription(description: String) {
        productDescription.value = description
    }

    fun setProductQuantity(quantity: String) {
        productQuantity.value = quantity
    }

    fun setProductPrice(price: String) {
        productPrice.value = price
    }

    fun addImages(imagesUri: List<Uri>) {
        val list = productPhotos.value.toMutableList()
        list.addAll(imagesUri)
        productPhotos.value = list

    }

    //TODO Insertar categorias
    fun saveProduct() = viewModelScope.launch {
        val product = productRepository.addProduct(
            name = productName.value,
            description = productDescription.value,
            quantity = productQuantity.value.toInt(),
            price = productPrice.value.toDouble(),
            template = true,
            persistApi = true,
            category = "General",
            invoice = null
        )
        if (product is Response.Success) {
            val idProduct = product.data.id
            val tempFiles: MutableList<File> = mutableListOf()

            // Iterar sobre cada URI en la lista productPhotos
            for ((index, uri) in productPhotos.value.withIndex()) {
                val inputStream = context.contentResolver.openInputStream(uri)
                inputStream.use { input ->
                    input?.let {
                        // Leer la imagen desde el URI y convertirla en un bitmap
                        val bitmap = BitmapFactory.decodeStream(input)

                        // Crear un archivo temporal para almacenar la imagen
                        //val tempFile = File.createTempFile("${idProduct}_${index}_image", ".png", context.cacheDir)

                        val fileName = "${idProduct}_${index}_image.png"
                        val tempFile = File(context.cacheDir, fileName)
                        tempFile.deleteOnExit()

                        // Escribir el bitmap en el archivo temporal
                        FileOutputStream(tempFile).use { outputStream ->
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        }

                        // Agregar el archivo temporal a la lista de archivos temporales
                        tempFiles.add(tempFile)
                    }
                }
            }

            // Subir los archivos temporales
            for (tempFile in tempFiles) {
                fileRepository.uploadImage2(tempFile)
            }

        }

    }

}