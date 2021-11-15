package com.artelsv.petprojectsecond.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalMarginItemDecoration(
    private val horizontalMarginInPx: Int,
    private val horizontal: Boolean = true,
    private val vertical: Boolean = true
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        if (horizontal) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }

        if (vertical) {
            outRect.top = horizontalMarginInPx
            outRect.bottom = horizontalMarginInPx
        }
    }
}