package com.jj.kurly.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingDto(
    @SerialName("next_page")
    val nextPage: Int
)