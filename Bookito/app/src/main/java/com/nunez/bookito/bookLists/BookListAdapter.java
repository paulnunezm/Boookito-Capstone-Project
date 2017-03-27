package com.nunez.bookito.bookLists;

import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.nunez.bookito.R;
import com.nunez.bookito.entities.Book;

/**
 * Created by paulnunez on 3/26/17.
 */

class BookListAdapter extends FirebaseRecyclerAdapter<Book, BookListViewHolder> {
  private static final String TAG = "BookListAdapter";

  private final onShowListListerner listListerner;

  public interface onShowListListerner {
    void onListShowed();
  }

  /**
   * @param bookListRef             The Firebase location to watch for data changes. Can also be a slice of a location,
   *                        using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
   */
  public BookListAdapter(DatabaseReference bookListRef, onShowListListerner listerner) {
    // Call the super constructor with predefined values and the received bookListRef
    super(Book.class, R.layout.item_search, BookListViewHolder.class, bookListRef);
    Log.d(TAG, "BookListAdapter: ");
    this.listListerner = listerner;
    Log.d(TAG, "BookListAdapter: ");

  }

  @Override
  protected void populateViewHolder(BookListViewHolder viewHolder, Book model, int position) {
    viewHolder.setBook(model);
    listListerner.onListShowed();
  }
}
