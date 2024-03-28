package com.jj.kurly.core.domain

import com.jj.kurly.core.data.repository.UserDataRepository
import javax.inject.Inject

class SetProductWishedUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository
) {
    suspend operator fun invoke(productId: Int, isWished: Boolean) {
        userDataRepository.setProductWished(productId, isWished)
    }
}