package com.nunez.bookito.searchBooks

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.bookito.R
import com.nunez.bookito.entities.BookWrapper
import com.nunez.bookito.loadImage
import com.nunez.bookito.setTextAndContentDescription
import kotlinx.android.synthetic.main.item_search.view.*

/**
 * Created by paulnunez on 3/23/17.
 */

class SearchAdapter(private val listener: SearchViewHolder.SearchBookListener) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private lateinit var context: Context
    var bookWrappers: ArrayList<BookWrapper> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        context = parent.context
        val searchViewItem = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false)

        return SearchViewHolder(searchViewItem, listener)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = with(holder) {
        setBookWrapper(bookWrappers[position])
    }

    override fun getItemCount(): Int = bookWrappers.size

    fun addBooks(bookWrapper: ArrayList<BookWrapper>) {
        bookWrappers.addAll(bookWrapper)
        this.notifyDataSetChanged()
    }

    fun clear() {
        bookWrappers.clear()
        this.notifyDataSetChanged()
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
}
