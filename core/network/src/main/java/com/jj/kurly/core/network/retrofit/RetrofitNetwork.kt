package com.jj.kurly.core.network.retrofit

import com.jj.kurly.core.network.NetworkDataSource
import com.jj.kurly.core.network.model.ResponseDto
import com.jj.kurly.core.network.model.SectionDto
import com.jj.kurly.core.network.model.ProductDto
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RetrofitNetwork @Inject constructor(
    okHttpClient: OkHttpClient
) : NetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
        )
        .build()
        .create(NetworkApi::class.java)

    override suspend fun getSections(): List<SectionDto> {
        return networkApi.getSections().data
    }

    override suspend fun getSectionProducts(sectionId: Int): List<ProductDto> {
        return networkApi.getSectionProducts(sectionId).data
    }

    companion object {
        private const val BASE_URL = "https://kurly.com/"
    }
}

private interface NetworkApi {

    @GET("sections")
    suspend fun getSections(): ResponseDto<List<SectionDto>>


    @GET("section/products")
    suspend fun getSectionProducts(
        @Query("sectionId") sectionId: Int
    ): ResponseDto<List<ProductDto>>
}