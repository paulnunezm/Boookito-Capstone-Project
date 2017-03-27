package com.nunez.bookito.bookLists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nunez.bookito.R;
import com.nunez.bookito.entities.Book;
import com.nunez.bookito.entities.BookWrapper;
import com.squareup.picasso.Picasso;

/**
 * Created by paulnunez on 3/26/17.
 */

class BookListViewHolder extends RecyclerView.ViewHolder {

  public interface SearchBookListener {
    void onAddToClickListener(BookWrapper bookWrapper);
  }

  private SearchBookListener listener;

  TextView    title;
  TextView    author;
  RatingBar   rating;
  ImageView   cover;
  Button      addTo;
  Context     context;

  public BookListViewHolder(View itemView) {
    super(itemView);

//    this.listener = listener;
    context = itemView.getContext();
    title = (TextView) itemView.findViewById(R.id.txt_title);
    author = (TextView) itemView.findViewById(R.id.txt_author);
    rating = (RatingBar) itemView.findViewById(R.id.rating_stars);
    cover = (ImageView) itemView.findViewById(R.id.img_cover);
    addTo = (Button) itemView.findViewById(R.id.btn_add_to);
  }

  void setBook(Book listBook){
    Book book = listBook;
    title.setText(book.getTitle());
    author.setText(book.getAuthor().getName()); // TODO: add the by formatter
    rating.setRating(Float.parseFloat(book.getAverageRating()));
    Picasso.with(context).load(book.getImageUrl()).fit().into(cover);

//    addTo.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        listener.onAddToClickListener(item);
//      }
//    });
  }
}