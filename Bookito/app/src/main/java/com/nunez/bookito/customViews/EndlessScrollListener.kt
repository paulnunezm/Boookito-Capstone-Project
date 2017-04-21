package com.nunez.bookito.customViews

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by paulnunez on 4/9/17.
 */
class EndlessScrollListener(val listener: () -> Unit) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibileItemCount: Int = recyclerView?.layoutManager?.childCount!!
        val totalItemCount: Int = recyclerView?.layoutManager?.itemCount!!
        val pastVisibleItems: Int = (recyclerView?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (visibileItemCount + pastVisibleItems >= totalItemCount) listener.invoke()

    }

    fun onBottomListener(listener: () -> Unit) {}
}
