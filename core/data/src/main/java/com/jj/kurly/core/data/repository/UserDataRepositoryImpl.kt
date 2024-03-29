package com.jj.kurly.core.data.repository

import com.jj.kurly.core.data.model.asDomainModel
import com.jj.kurly.core.datastore.PreferencesDataStore
import com.jj.kurly.core.datastore.model.UserDataDto
import com.jj.kurly.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UserDataRepositoryImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : UserDataRepository {

    override val userData: Flow<UserData> =
        preferencesDataStore.userData.map(UserDataDto::asDomainModel)

    override suspend fun setProductWished(productId: Int, isWished: Boolean) {
        preferencesDataStore.setProductWished(productId = productId, isWished = isWished)
    }
}