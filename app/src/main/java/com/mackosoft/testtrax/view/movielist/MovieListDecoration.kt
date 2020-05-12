package com.mackosoft.testtrax.view.movielist

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mackosoft.testtrax.extension.asPx

// sticky header solution from: https://gist.github.com/filipkowicz/1a769001fae407b8813ab4387c42fcbd
class MovieListDecoration() : RecyclerView.ItemDecoration() {

    private val marginTop       = 8.asPx
    private val marginStart     = 16.asPx
    private val marginEnd       = 16.asPx
    private val marginBottom    = 8.asPx


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        outRect.top = marginTop
        outRect.left = marginStart
        outRect.right = marginEnd
        outRect.bottom = marginBottom

    }

}