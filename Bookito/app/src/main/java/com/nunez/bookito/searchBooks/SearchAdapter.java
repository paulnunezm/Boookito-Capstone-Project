package com.nunez.bookito.searchBooks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nunez.bookito.R;
import com.nunez.bookito.entities.Book;
import com.nunez.bookito.entities.BookWrapper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by paulnunez on 3/23/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


  private ArrayList<BookWrapper> bookWrappers;
  private Context                context;

  public SearchAdapter(ArrayList<BookWrapper> bookWrappers) {
    this.bookWrappers = bookWrappers;
  }

  @Override
  public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View searchViewItem = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
    return new SearchViewHolder(searchViewItem);
  }

  @Override
  public void onBindViewHolder(SearchViewHolder holder, int position) {
    Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
    holder.setBookWrapper(bookWrappers.get(position));
  }

  @Override
  public int getItemCount() {
    return bookWrappers.size();
  }

  public static class SearchViewHolder extends RecyclerView.ViewHolder {

    BookWrapper item;
    TextView    title;
    TextView    author;
    RatingBar   rating;
    ImageView   cover;
    Context     context;

    public SearchViewHolder(View itemView) {
      super(itemView);
      context = itemView.getContext();
      title = (TextView) itemView.findViewById(R.id.txt_title);
      author = (TextView) itemView.findViewById(R.id.txt_author);
      rating = (RatingBar) itemView.findViewById(R.id.rating_stars);
      cover = (ImageView) itemView.findViewById(R.id.img_cover);
    }

    public void setBookWrapper(BookWrapper item) {
      this.item = item;

      Book book = item.getBook();
      title.setText(book.getTitle());
      author.setText(item.getBook().getAuthor().getName());
      rating.setRating(Float.parseFloat(item.getAverageRating()));

      Picasso.with(context).load(book.getImageUrl()).fit().into(cover);
    }
  }
}
