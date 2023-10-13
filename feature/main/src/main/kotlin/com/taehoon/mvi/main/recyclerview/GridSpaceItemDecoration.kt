package com.taehoon.mvi.main.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpaceItemDecoration(private val horizontalSpace: Int, private val topSpace: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val layoutParams = (view.layoutParams as GridLayoutManager.LayoutParams)
        val index: Int = layoutParams.spanIndex
        val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
        val firstIndex = 0
        val lastIndex = spanCount - 1

        when (index) {
            firstIndex -> {
                outRect.right = horizontalSpace / 2
            }

            lastIndex -> {
                outRect.left = horizontalSpace / 2
            }

            else -> {
                outRect.right = horizontalSpace / 2
                outRect.left = horizontalSpace / 2
            }
        }

        val position: Int = parent.getChildLayoutPosition(view)

        if (position < spanCount) {
            outRect.top = 0
        } else {
            outRect.top = topSpace
        }
    }
}