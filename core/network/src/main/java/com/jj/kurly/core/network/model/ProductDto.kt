package com.jj.kurly.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val name: String,
    val image: String,
    val originalPrice: Int,
    val discountedPrice: Int? = null,
    val isSoldOut: Boolean
)