package com.mackosoft.testtrax.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.imageview.ShapeableImageView

class NetworkImageView(context: Context, attrs: AttributeSet?) : ShapeableImageView(context, attrs) {

    private var onImageLoaded : (() -> Unit)? = null


    fun loadNetworkImage(url: String) {
        Glide.with(this)
            .load(url)
            .apply(
                RequestOptions().dontTransform()
            )
            .addListener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onImageLoaded?.invoke()
                    return false
                }
            }).into(this)
    }


    fun doOnImageLoaded(f: () -> Unit) { onImageLoaded = f }

}