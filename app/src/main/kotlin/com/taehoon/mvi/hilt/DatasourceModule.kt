package com.taehoon.mvi.hilt

import com.taehoon.mvi.datasource.datasource.GalleryRemoteDatasourceImpl
import com.taehoon.mvi.repository.datasource.GalleryRemoteDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DatasourceModule {

    @Binds
    abstract fun bindGalleryRemoteDatasource(
        galleryRemoteDatasourceImpl: GalleryRemoteDatasourceImpl
    ): GalleryRemoteDatasource
}