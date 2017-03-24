package com.nunez.bookito.searchBooks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nunez.bookito.R;
import com.nunez.bookito.entities.Book;
import com.nunez.bookito.entities.BookWrapper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by paulnunez on 3/23/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


  private ArrayList<BookWrapper>              bookWrappers;
  private Context                             context;
  private SearchViewHolder.SearchBookListener listener;

  public SearchAdapter(ArrayList<BookWrapper> bookWrappers, SearchViewHolder.SearchBookListener listener) {
    this.bookWrappers = bookWrappers;
    this.listener = listener;
  }

  @Override
  public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View searchViewItem = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);

    return new SearchViewHolder(searchViewItem, listener);
  }

  @Override
  public void onBindViewHolder(SearchViewHolder holder, int position) {
    holder.setBookWrapper(bookWrappers.get(position));
  }

  @Override
  public int getItemCount() {
    return bookWrappers.size();
  }

  public static class SearchViewHolder extends RecyclerView.ViewHolder {

    private SearchBookListener listener;
    BookWrapper item;
    TextView    title;
    TextView    author;
    RatingBar   rating;
    ImageView   cover;
    Button  addTo;
    Context context;

    public interface SearchBookListener {
      void onAddToClickListener(BookWrapper bookWrapper);
    }

    public SearchViewHolder(View itemView, SearchBookListener listener) {
      super(itemView);
      this.listener = listener;
      context = itemView.getContext();
      title = (TextView) itemView.findViewById(R.id.txt_title);
      author = (TextView) itemView.findViewById(R.id.txt_author);
      rating = (RatingBar) itemView.findViewById(R.id.rating_stars);
      cover = (ImageView) itemView.findViewById(R.id.img_cover);
      addTo = (Button) itemView.findViewById(R.id.btn_add_to);
    }

    public void setBookWrapper(final BookWrapper item) {
      this.item = item;
      Book book = item.getBook();
      title.setText(book.getTitle());
      author.setText(item.getBook().getAuthor().getName()); // TODO: add the by formatter
      rating.setRating(Float.parseFloat(item.getAverageRating()));
      Picasso.with(context).load(book.getImageUrl()).fit().into(cover);

      addTo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onAddToClickListener(item);
        }
      });
    }

  }
}
