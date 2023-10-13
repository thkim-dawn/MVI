package com.taehoon.mvi.datasource.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.taehoon.mvi.datasource.api.PicsumApi
import com.taehoon.mvi.datasource.response.ResponseImage
import com.taehoon.mvi.repository.GalleryRepositoryImpl.Companion.DEFAULT_PAGE_SIZE
import com.taehoon.mvi.repository.data.GalleryRepositoryData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GalleryPagingSourceTest {

    @get:Rule
    val instanceExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var picsumApi: PicsumApi

    private lateinit var galleryPagingSource: GalleryPagingSource
    private lateinit var pager: TestPager<Int, GalleryRepositoryData>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        galleryPagingSource = GalleryPagingSource(picsumApi)
        pager = TestPager(PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false), galleryPagingSource)
    }


    @Test
    fun `GalleryPagingSource_RuntimeException_Test`() = runTest(UnconfinedTestDispatcher()) {
        val error = RuntimeException()

        Mockito.`when`(picsumApi.fetchImage())
            .thenThrow(error)

        val expectedResult = PagingSource.LoadResult.Error<Int, GalleryRepositoryData>(error)

        Assert.assertEquals(
            expectedResult, pager.refresh()
        )
    }

    @Test
    fun `GalleryPagingSource_Return_Null_Test`() = runTest(UnconfinedTestDispatcher()) {

        Mockito.`when`(picsumApi.fetchImage())
            .thenReturn(null)

        val expectedResult = PagingSource.LoadResult.Error<Int, GalleryRepositoryData>(NullPointerException())

        Assert.assertEquals(
            expectedResult.throwable.cause, (pager.refresh() as PagingSource.LoadResult.Error).throwable.cause
        )
    }


    @Test
    fun `GalleryPagingSource_Initial_Load_Test`() = runTest(UnconfinedTestDispatcher()) {
        var pageNum = 1

        val list = MutableList(DEFAULT_PAGE_SIZE) {
            ResponseImage("${it + 1}", "https://${it + 1}/500/500")
        }

        Mockito.`when`(picsumApi.fetchImage(pageNum++))
            .thenReturn(list)

        val expectedResult = PagingSource.LoadResult.Page(
            data = list.map { it.mapToImageRepositoryData() },
            prevKey = null,
            nextKey = pageNum
        )

        Assert.assertEquals(
            expectedResult, pager.refresh()
        )
    }


    @Test
    fun `GalleryPagingSource_Append_Load_Test`() = runTest(UnconfinedTestDispatcher()) {
        var pageNum = 1

        val firstList = MutableList(DEFAULT_PAGE_SIZE) {
            ResponseImage("${it + 1}", "https://${it + 1}/500/500")
        }

        val secondList = MutableList(DEFAULT_PAGE_SIZE) {
            ResponseImage("${it + 1 + 20}", "https://${it + 1 + 20}/500/500")
        }

        Mockito.`when`(picsumApi.fetchImage(pageNum++))
            .thenReturn(firstList)

        Mockito.`when`(picsumApi.fetchImage(pageNum++))
            .thenReturn(secondList)

        val expectedResult = PagingSource.LoadResult.Page(
            data = secondList.map { it.mapToImageRepositoryData() },
            prevKey = null,
            nextKey = pageNum
        )

        Assert.assertEquals(
            expectedResult, with(pager) {
                refresh()
                append()
            }
        )
    }
}