package com.jj.kurly.core.data.repository

import com.jj.kurly.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>

    suspend fun setProductWished(productId: Int, isWished: Boolean)
}