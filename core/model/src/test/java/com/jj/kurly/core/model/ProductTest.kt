package com.jj.kurly.core.model

import com.jj.kurly.core.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductTest {

    @Test
    fun discountedPriceHalf_discountRate50() {
        val product = Product(
            id = 1,
            name = "name",
            image = "url",
            originalPrice = 1000,
            discountedPrice = 500,
            isSoldOut = false
        )
        assertEquals(50, product.discountRate)
    }

    @Test
    fun discountedPriceNull_discountRate0() {
        val product = Product(
            id = 1,
            name = "name",
            image = "url",
            originalPrice = 1000,
            discountedPrice = null,
            isSoldOut = false
        )
        assertEquals(0, product.discountRate)
    }

    @Test
    fun discountedPriceZero_discountRate100() {
        val product = Product(
            id = 1,
            name = "name",
            image = "url",
            originalPrice = 1000,
            discountedPrice = 0,
            isSoldOut = false
        )
        assertEquals(100, product.discountRate)
    }
}