package com.okamiko.core.utils

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpaceDecoration(
    private val spanCount: Int,
    context: Context,
    spacingInDp: Float,
) : RecyclerView.ItemDecoration() {

    private val spacing: Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        spacingInDp,
        context.resources.displayMetrics
    ).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left = column * spacing / spanCount
        outRect.right = spacing - (column + 1) * spacing / spanCount
        if (position >= spanCount) {
            outRect.top = spacing
        }
    }
}
