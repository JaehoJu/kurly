package com.jj.kurly.core.data.di

import com.jj.kurly.core.data.repository.SectionRepository
import com.jj.kurly.core.data.repository.SectionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsSectionRepository(
        sectionRepository: SectionRepositoryImpl
    ): SectionRepository
}