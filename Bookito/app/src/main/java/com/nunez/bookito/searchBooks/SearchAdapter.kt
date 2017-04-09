package com.nunez.bookito.searchBooks

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.nunez.bookito.R
import com.nunez.bookito.entities.BookWrapper
import com.nunez.bookito.inflate
import com.nunez.bookito.loadImage
import com.nunez.bookito.setTextAndContentDescription
import kotlinx.android.synthetic.main.item_search.view.*

/**
 * Created by paulnunez on 3/23/17.
 */

class SearchAdapter(private val listener: SearchViewHolder.SearchBookListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val VIEW_TYPE_ITEM = 0
        val VIEW_TYPE_PROGRESS = 1
    }

    private lateinit var context: Context
    var bookWrappers: ArrayList<BookWrapper> = ArrayList()
    var progressBarEnabled = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
//        val searchViewItem = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false)

        if (viewType.equals(VIEW_TYPE_ITEM)) {
            return SearchViewHolder(parent.inflate(R.layout.item_search), listener)
        } else {
            return ProgressViewHolder(parent.inflate(R.layout.item_progress));
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchViewHolder) {
            holder.setBookWrapper(bookWrappers[position])
        }
    }

    override fun getItemCount(): Int = if (progressBarEnabled) bookWrappers.size + 1 else bookWrappers.size

    override fun getItemViewType(position: Int): Int {
        return if (position >= bookWrappers.size) VIEW_TYPE_PROGRESS else VIEW_TYPE_ITEM
    }

    fun addBooks(bookWrapper: ArrayList<BookWrapper>) {
        bookWrappers.addAll(bookWrapper)
        this.notifyDataSetChanged()
    }

    fun clear() {
        bookWrappers.clear()
        this.notifyDataSetChanged()
    }

    fun enableProgressBar() {
        progressBarEnabled = true
    }

    fun disableProgressBar() {
        progressBarEnabled = false
    }

    class SearchViewHolder(itemView: View, private val listener: SearchViewHolder.SearchBookListener) : RecyclerView.ViewHolder(itemView) {

        interface SearchBookListener {
            fun onAddToClickListener(bookWrapper: BookWrapper)
        }

        fun setBookWrapper(item: BookWrapper) {
            val book = item.getBook()

            with(book) {
                itemView.txt_title.setTextAndContentDescription(title)
                itemView.txt_author.setTextAndContentDescription(author.name)
                itemView.img_cover.loadImage(imageUrl)
                itemView.rating_stars.contentDescription = item.averageRating
                itemView.rating_stars.rating = item.averageRating.toFloat()
                itemView.btn_add_to.setOnClickListener { listener.onAddToClickListener(item) }
            }
        }
    }

    class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
