package com.jj.kurly.core.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PreferencesDataStore @Inject constructor() {

    val userData: Flow<UserDataDto>
        get() = flowOf(UserDataDto(wishedProducts = emptySet()))

    suspend fun setProductWished(productId: Int, isWished: Boolean) {

    }
}