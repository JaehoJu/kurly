package com.jj.kurly.core.data.model

import com.jj.kurly.core.model.SectionInfo
import com.jj.kurly.core.model.SectionType
import com.jj.kurly.core.network.model.SectionDto

fun SectionDto.asDomainModel() = SectionInfo(
    title = title,
    id = id,
    type = when (type) {
        "vertical" -> SectionType.VERTICAL
        "horizontal" -> SectionType.HORIZONTAL
        "grid" -> SectionType.GRID
        else -> SectionType.UNKNOWN
    },
    url = url
)
