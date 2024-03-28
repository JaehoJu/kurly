package com.kurly.android.mockserver.core

internal interface FileProvider {
    fun getJsonFromAsset(filePath: String): String?
}
