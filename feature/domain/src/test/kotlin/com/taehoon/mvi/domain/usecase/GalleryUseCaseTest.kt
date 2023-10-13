package com.taehoon.mvi.domain.usecase

import androidx.paging.PagingData
import com.taehoon.mvi.domain.entity.GalleryEntity
import com.taehoon.mvi.domain.repository.GalleryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GalleryUseCaseTest {

    @Mock
    private lateinit var galleryRepository: GalleryRepository
    private lateinit var galleryUseCase: GalleryUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        galleryUseCase = GalleryUseCase(galleryRepository)
    }

    @Test
    fun `GalleryUseCase_GetGalleryPagingData_Test`() = runTest {
        val list = MutableList(20) {
            GalleryEntity("${it + 1}", "https://${it + 1}/500/500")
        }
        val pagingData = PagingData.from(list)

        Mockito.`when`(galleryRepository.getGalleryPagingData())
            .thenReturn(flowOf(pagingData))

        val expectedResult = galleryUseCase.getGalleryPagingData().first()

        Assert.assertEquals(
            expectedResult, pagingData
        )
    }
}