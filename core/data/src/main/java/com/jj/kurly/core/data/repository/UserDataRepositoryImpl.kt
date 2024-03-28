package com.jj.kurly.core.data.repository

import com.jj.kurly.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

internal class UserDataRepositoryImpl @Inject constructor() : UserDataRepository {

    // TODO:
    override val userData: Flow<UserData>
        get() = emptyFlow()

    // TODO:
    override suspend fun setProductWished(productId: Int, isWished: Boolean) {
    }
}