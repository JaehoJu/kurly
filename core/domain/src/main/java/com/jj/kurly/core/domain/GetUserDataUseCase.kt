package com.jj.kurly.core.domain

import com.jj.kurly.core.data.repository.UserDataRepository
import com.jj.kurly.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(): Flow<UserData> = userDataRepository.userData
}