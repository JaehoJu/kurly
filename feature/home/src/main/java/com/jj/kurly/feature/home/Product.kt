package com.jj.kurly.feature.home

data class Product(
    val id: Int,
    val name: String,
    val image: String,
    val originalPrice: Int,
    val discountedPrice: Int,
    val isSoldOut: Boolean
) {

    val discountRate: Int = discountedPrice * 100 / originalPrice
}