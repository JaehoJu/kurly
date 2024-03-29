package com.jj.kurly.core.datastore

import androidx.datastore.core.DataStore
import com.jj.kurly.core.datastore.model.UserDataDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    val userData: Flow<UserDataDto> = userPreferences.data
        .map {
            UserDataDto(
                wishedProducts = it.wishedProductIdsMap.keys
            )
        }

    suspend fun setProductWished(productId: Int, isWished: Boolean) {
        userPreferences.updateData {
            if (isWished) {
                it.toBuilder()
                    .putWishedProductIds(productId, true)
                    .build()
            } else {
                it.toBuilder()
                    .removeWishedProductIds(productId)
                    .build()
            }
        }
    }
}