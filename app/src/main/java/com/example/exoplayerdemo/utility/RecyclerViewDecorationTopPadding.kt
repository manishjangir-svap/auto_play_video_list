package com.example.exoplayerdemo.utility

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDecorationTopPadding(private val context: Context, private val padding: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if(parent.getChildAdapterPosition(view) > 0) outRect.top = (padding * context.resources.displayMetrics.density).toInt()
    }
}