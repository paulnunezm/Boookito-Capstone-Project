package com.nunez.bookito.bookLists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.nunez.bookito.R;
import com.nunez.bookito.entities.Book;

/**
 * Created by paulnunez on 3/26/17.
 */

class BookListAdapter extends FirebaseRecyclerAdapter<Book, BookListViewHolder> {
  private static final String TAG = "BookListAdapter";

  private final DatabaseReference bookListRef;
  private final onShowListListerner listListerner;
  private BookListViewHolder.BookListItemListener itemSelectedListener;

  interface onShowListListerner {
    void onListShowed();
  }


  BookListAdapter(DatabaseReference bookListRef, onShowListListerner listerner, BookListViewHolder.BookListItemListener itemSelectedListener) {
    // Call the super constructor with predefined values and the received bookListRef
    super(Book.class, R.layout.item_book_on_list, BookListViewHolder.class, bookListRef);
    this.bookListRef = bookListRef;
    this.listListerner = listerner;
    this.itemSelectedListener = itemSelectedListener;
  }

  @Override
  public BookListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // Although the FirebaseRecycler Adapter creates this by it self
    // it is handy for us to override this method to be able to pass
    // the context we're into and a listener. :D

    Context context        = parent.getContext();
    View    searchViewItem = LayoutInflater.from(context).inflate(R.layout.item_book_on_list, parent, false);
    return new BookListViewHolder(searchViewItem, itemSelectedListener, context); 
  }

  @Override
  protected void populateViewHolder(BookListViewHolder viewHolder, Book model, int position) {
    viewHolder.setBook(model);
    listListerner.onListShowed();
  }
}
