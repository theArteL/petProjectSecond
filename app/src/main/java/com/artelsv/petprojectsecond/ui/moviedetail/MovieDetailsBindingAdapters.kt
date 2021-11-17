package com.artelsv.petprojectsecond.ui.moviedetail

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.textview.MaterialTextView

@BindingAdapter("imageUrl")
fun loadImage(view: AppCompatImageView, url: String?) {
    view.load(url)
}

@BindingAdapter("colorRes")
fun colorRes(view: MaterialTextView, res: Int?) {
    res?.let { view.setTextColor(view.context.getColor(res)) }
}