package com.nunez.bookito.bookLists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nunez.bookito.R;
import com.nunez.bookito.entities.Book;
import com.squareup.picasso.Picasso;

/**
 * Created by paulnunez on 3/26/17.
 */

class BookListViewHolder extends RecyclerView.ViewHolder {

  public interface BookListItemListener {
    void onItemClickListener(Book book);
  }

  BookListItemListener listener;
  TextView             title;
  TextView             author;
  RatingBar            rating;
  ImageView            cover;
  Context              context;
  View                 layout;
  Book                 book;

  public BookListViewHolder(View itemView, BookListItemListener listItemListerner, Context context) {
    super(itemView);

    this.context = itemView.getContext();
    this.context = context;
    listener= listItemListerner;
    layout = itemView.findViewById(R.id.layout);
    title = (TextView) itemView.findViewById(R.id.txt_title);
    author = (TextView) itemView.findViewById(R.id.txt_author);
    rating = (RatingBar) itemView.findViewById(R.id.rating_stars);
    cover = (ImageView) itemView.findViewById(R.id.img_cover);
  }

  void setBook(final Book listBook) {
    book = listBook;
    title.setText(book.getTitle());
    author.setText(context.getResources()
        .getString(R.string.search_activity_by_author, book.getAuthor().getName()));
    rating.setRating(Float.parseFloat(book.getAverageRating()));
    Picasso.with(context).load(book.getImageUrl()).fit().into(cover);

    layout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onItemClickListener(book);
      }
    });
  }
}