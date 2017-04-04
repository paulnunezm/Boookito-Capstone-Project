package com.nunez.bookito.homescreenWidget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nunez.bookito.R;
import com.nunez.bookito.entities.Book;
import com.nunez.bookito.repositories.FirebaseNodes;

import java.util.ArrayList;

/**
 * Created by paulnunez on 4/1/17.
 */

/**
 * An implementation of {@link android.widget.RemoteViewsService.RemoteViewsFactory}
 * which is a thin wrapper around the {@link android.widget.Adapter} interface.
 * <p>
 * This returns a collection as {@link RemoteViews} to include it in the widget.
 * <p>
 * In simple termns it gets the data to populate the widget views.
 */
class BooksRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory, ValueEventListener {
  private static final String TAG = "BooksRemoteViewsFactory";

  private final Context           context;
  private final Intent            intent;
  private       DatabaseReference db;
  private ArrayList<Book> books = new ArrayList<Book>();


  BooksRemoteViewsFactory(Context context, Intent intent) {
    this.context = context;
    this.intent = intent;
  }

  @Override
  public void onCreate() {
    // In onCreate() you set up any connections / cursors to your data source. Heavy lifting,
    // for example downloading or creating content etc, should be deferred to onDataSetChanged()
    // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
    Log.i(TAG, "onCreate: ");
    getBooks();
  }

  private void getBooks() {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    if (user != null) {
      db = FirebaseDatabase.getInstance().getReference()
          .child(user.getUid())
          .child(FirebaseNodes.WISHLIST);
      db.addValueEventListener(this);
    }

    Log.d(TAG, "getBooks() size->" + books.size());
  }

  @Override
  public void onDataSetChanged() {
    Log.i(TAG, "onDataSetChanged: ");
    getBooks();
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public int getCount() {
    Log.d(TAG, "getCount: " + books.size());
    return books.size();
  }

  @Override
  public RemoteViews getViewAt(int position) {
    Log.d(TAG, "getViewAt()");
    Book book = new Book();
    try {
      book = books.get(position);

      // Construct a remote views item based on the app widget item XML file,
      // and set the text based on the position.
      RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);

      // set text to the views
      rv.setTextViewText(R.id.widget_txt_book_title, book.getTitle());
      rv.setTextViewText(R.id.widget_txt_author_name, book.getAuthor().getName());

      // set content description
      rv.setContentDescription(R.id.widget_txt_book_title, book.getTitle());
      rv.setContentDescription(R.id.widget_txt_author_name, book.getAuthor().getName());

      // set clickable button
      // Setting a fill-intent, which will be used to fill in the pending intent template
      // that is set on the collection view in Bookito provicer.
      Bundle extras = new Bundle();
      extras.putInt(BooksWidgetProvider.CLICKED_ITEM_ACTION, position);
      extras.putString(BooksWidgetProvider.CLICKED_ITEM_BOOK_ID, book.getTitle());
      Intent fillInIntent = new Intent();
      fillInIntent.putExtras(extras);

      rv.setOnClickFillInIntent(R.id.widget_btn_select, fillInIntent);
      return rv;

    } catch (IndexOutOfBoundsException e) {
      Log.e(TAG, "getViewAt: ", e);
    }

    return null;
  }

  @Override
  public RemoteViews getLoadingView() {
    return null;
  }

  @Override
  public int getViewTypeCount() {
    return 1;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public void onDataChange(DataSnapshot dataSnapshot) {
    books.clear();
    for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
      Book book = bookSnapshot.getValue(Book.class);
      books.add(book);
      Log.i(TAG, "onDataChange: " + book.getTitle());
    }
    Log.d(TAG, "onDataChange: bookSize ->" + books.size());
    db.removeEventListener(this);
  }

  @Override
  public void onCancelled(DatabaseError databaseError) {

  }
}
