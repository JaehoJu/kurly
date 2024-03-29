package com.jj.kurly.core.data.model

import com.jj.kurly.core.datastore.UserDataDto
import com.jj.kurly.core.model.UserData

fun UserDataDto.asDomainModel() = UserData(
    wishedProducts = wishedProducts
)