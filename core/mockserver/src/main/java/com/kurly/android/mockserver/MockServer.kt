package com.kurly.android.mockserver

import com.kurly.android.mockserver.core.FileProvider
import okhttp3.Request

internal class MockServer constructor(
    private val fileProvider: FileProvider
) {

    fun get(request: Request): String? {
        val url = request.url
        return when (url.encodedPath) {
            "/sections" -> getSections(url.queryParameter("page")?.toIntOrNull() ?: 1)
            "/section/products" -> getSectionProducts(url.queryParameter("sectionId")?.toIntOrNull())
            else -> null
        }
    }

    private fun getSections(page: Int): String? {
        return try {
            fileProvider.getJsonFromAsset("sections/sections_$page.json")
        } catch (e: Exception) {
            null
        }
    }

    private fun getSectionProducts(sectionId: Int?): String? {
        if (sectionId == null) {
            return null
        }
        return try {
            fileProvider.getJsonFromAsset("section/products/section_products_$sectionId.json")
        } catch (e: Exception) {
            null
        }
    }
}
