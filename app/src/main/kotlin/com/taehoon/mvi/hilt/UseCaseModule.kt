package com.taehoon.mvi.hilt

import com.taehoon.mvi.domain.repository.GalleryRepository
import com.taehoon.mvi.domain.usecase.GalleryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module(includes = [RepositoryModule::class])
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGalleryUseCase(galleryRepository: GalleryRepository): GalleryUseCase {
        return GalleryUseCase(galleryRepository)
    }
}