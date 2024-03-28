package com.jj.kurly.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto<T>(
    val data: T,
    val paging: PagingDto? = null
)