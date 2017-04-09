package com.nunez.bookito

import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

/**
 * Created by paulnunez on 4/9/17.
 */

fun TextView.setTextAndContentDescription(text: String){
    this.text = text
    this.contentDescription = text
}

fun ImageView.loadImage(url: String){
    Picasso.with(this.context).load(url).fit().into(this)
}