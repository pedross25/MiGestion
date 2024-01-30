package com.example.migestion.data.network

object HttpRoutes {
    private const val BASE_URL = "http://10.0.2.2:8080"

    object Customer {
        private const val BASE_CUSTOMER = "$BASE_URL/customer"
        const val CREATE = "$BASE_CUSTOMER/create"
        const val GETALL = "$BASE_CUSTOMER/getAll"
    }

    object Product {
        private const val BASE_PRODUCT = "$BASE_URL/product"
        const val GETALL = "${Product.BASE_PRODUCT}/getAll"
        const val CREATE = "${Product.BASE_PRODUCT}/create"
    }
}