package com.jj.kurly.core.data.model

import com.jj.kurly.core.model.Product
import com.jj.kurly.core.network.model.ProductDto

fun ProductDto.asDomainModel() = Product(
    id = id,
    name = name,
    image = image,
    originalPrice = originalPrice,
    discountedPrice = discountedPrice,
    isSoldOut = isSoldOut
)