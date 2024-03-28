package com.jj.kurly.core.domain

import com.jj.kurly.core.data.repository.SectionRepository
import com.jj.kurly.core.model.Section
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(
    private val sectionRepository: SectionRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Section>> {
        return sectionRepository.getSectionInfos()
            .flatMapLatest {
                flowOf(
                    it.map { sectionInfo ->
                        Section(
                            info = sectionInfo,
                            products = sectionRepository.getProducts(sectionInfo.url).first()
                        )
                    }
                )
            }
    }
}