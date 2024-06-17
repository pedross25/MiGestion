package com.example.migestion.data.network

object HttpRoutes {
    private const val BASE_URL = "http://93.93.115.184:8080"

    object Auth {
        private const val BASE_AUTH = "$BASE_URL/auth"
        const val LOGIN = "$BASE_AUTH/login"
        const val REGISTER = "$BASE_AUTH/register"
    }

    object Customer {
        private const val BASE_CUSTOMER = "$BASE_URL/customer"
        const val CREATE = "$BASE_CUSTOMER/create"
        const val GETALL = "$BASE_CUSTOMER/getAll"
    }

    object Product {
        private const val BASE_PRODUCT = "$BASE_URL/product"
        const val GETALL = "$BASE_PRODUCT/getAll"
        const val CREATE = "$BASE_PRODUCT/create"
    }

    object Image {
        private const val BASE_IMAGE = "$BASE_URL/file"
        const val UPLOAD = "$BASE_IMAGE/upload"
        const val GET = "$BASE_IMAGE/"
    }

    object Invoice {
        private const val BASE_INVOICE = "$BASE_URL/invoice"
        const val CREATE = "$BASE_INVOICE/create"
        const val GETALL = "$BASE_INVOICE/getAll"
    }
}