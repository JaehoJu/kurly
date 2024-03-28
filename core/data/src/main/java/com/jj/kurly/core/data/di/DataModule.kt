package com.jj.kurly.core.data.di

import com.jj.kurly.core.data.repository.SectionRepository
import com.jj.kurly.core.data.repository.SectionRepositoryImpl
import com.jj.kurly.core.data.repository.UserDataRepository
import com.jj.kurly.core.data.repository.UserDataRepositoryImpl
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

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository
}