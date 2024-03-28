package com.kurly.android.mockserver.core

internal class TestAssetFileProvider : FileProvider {

    override fun getJsonFromAsset(filePath: String): String? {
        val inputStream = javaClass.classLoader?.getResourceAsStream(filePath) ?: return null
        return inputStream.bufferedReader().use { it.readText() }
    }
}
