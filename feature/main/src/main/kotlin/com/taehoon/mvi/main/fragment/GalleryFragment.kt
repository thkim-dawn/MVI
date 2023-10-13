package com.taehoon.mvi.main.fragment

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taehoon.common.base.BaseFragment
import com.taehoon.common.convertDpToPx
import com.taehoon.common.mvi.observe
import com.taehoon.mvi.main.R
import com.taehoon.mvi.main.databinding.FragmentGalleryBinding
import com.taehoon.mvi.main.databinding.LayoutInfoBinding
import com.taehoon.mvi.main.recyclerview.GalleryRecyclerAdapter
import com.taehoon.mvi.main.recyclerview.GridSpaceItemDecoration
import com.taehoon.mvi.presentation.GalleryIntentOnStart
import com.taehoon.mvi.presentation.GallerySideEffect
import com.taehoon.mvi.presentation.GalleryViewModel
import com.taehoon.mvi.presentation.GalleryViewState
import com.taehoon.mvi.presentation.GalleryViewStateType
import com.taehoon.mvi.presentation.data.GalleryItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@FlowPreview
@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding, GalleryViewState, GallerySideEffect>() {

    private val galleryViewModel: GalleryViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var galleryRecyclerAdapter: GalleryRecyclerAdapter
    private lateinit var layoutInfoBinding: LayoutInfoBinding
    override fun generateViewBinding(): FragmentGalleryBinding = FragmentGalleryBinding.inflate(layoutInflater)
    override fun initView() {
        galleryViewModel.observe(viewState = ::render, sideEffect = ::handleSideEffect, lifecycleOwner = this)
        initRecyclerView()
        initInfoLayout()
    }

    override fun afterInitView() {
        //do nothing
    }

    override fun handleSideEffect(sideEffect: GallerySideEffect) {
        when (sideEffect) {
            is GallerySideEffect.ShowErrorToast -> Toast.makeText(this.context, R.string.info_error, Toast.LENGTH_SHORT).show()
            is GallerySideEffect.ShowLoading -> showLoading(sideEffect.isShowLoading)
        }
    }

    override fun render(viewState: GalleryViewState) {
        showLoading(viewState.showLoading)
        when (viewState.stateType) {
            GalleryViewStateType.Initial -> {}
            GalleryViewStateType.LoadedGalleryList -> renderLoadedFavoriteMediaItemList(viewState.galleryPagingData)
        }
    }

    override fun onStart() {
        super.onStart()
        if (recyclerView.adapter?.itemCount == 0) {
            galleryViewModel.dispatcherIntent(GalleryIntentOnStart)
        }
    }

    private fun initInfoLayout() {
        layoutInfoBinding = binding.infoLayout
        layoutInfoBinding.infoTextView.text = getText(R.string.info_loading)
    }

    private fun initRecyclerView() {
        galleryRecyclerAdapter = GalleryRecyclerAdapter()
        recyclerView = binding.recyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = galleryRecyclerAdapter
            addItemDecoration(
                GridSpaceItemDecoration(convertDpToPx(requireContext(), 4), convertDpToPx(requireContext(), 4))
            )
        }
        lifecycleScope.launch {
            galleryRecyclerAdapter.loadStateFlow.collectLatest { loadStates ->
                showLoading(loadStates.refresh is LoadState.Loading)
                if (loadStates.refresh is LoadState.NotLoading)
                    layoutInfoBinding.root.visibility = View.GONE
                if (loadStates.refresh is LoadState.Error)
                    renderEmptyInfo()
            }
        }
    }

    private fun renderLoadedFavoriteMediaItemList(galleryPagingData: PagingData<GalleryItem>?) {
        if (galleryPagingData == null) {
            renderEmptyInfo()
        } else {
            recyclerView.visibility = View.VISIBLE
            galleryRecyclerAdapter.submitData(this.lifecycle, galleryPagingData)
        }
    }

    private fun renderEmptyInfo() {
        layoutInfoBinding.root.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        layoutInfoBinding.infoTextView.text = getString(R.string.info_error)
    }

    private fun showLoading(isShow: Boolean) {
        binding.progressLayout.root.visibility = if (isShow) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    companion object {
        fun newInstance(): GalleryFragment = GalleryFragment()
    }
}