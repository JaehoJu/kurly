package com.jj.kurly.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SectionDto(
    val title: String,
    val id: Int,
    val type: String,
    val url: String
)