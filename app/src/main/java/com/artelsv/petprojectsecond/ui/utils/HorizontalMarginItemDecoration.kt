package com.artelsv.petprojectsecond.ui.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int, private val horizontal: Boolean = true, private val vertical: Boolean = true,
                                     private val horizontalMarginInPx: Int = context.resources.getDimension(horizontalMarginInDp).toInt()) : RecyclerView.ItemDecoration() {

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