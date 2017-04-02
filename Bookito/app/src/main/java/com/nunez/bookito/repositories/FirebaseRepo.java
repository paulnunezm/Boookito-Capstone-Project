package com.nunez.bookito.repositories;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nunez.bookito.entities.Book;
import com.nunez.bookito.homescreenWidget.BooksWidgetProvider;
import com.nunez.bookito.repositories.FirebaseNodes.BOOK_LISTS;

/**
 * Created by paulnunez on 3/25/17.
 */

public class FirebaseRepo implements ValueEventListener {
  private static final String       TAG      = "FirebaseRepo";
  private static       FirebaseRepo instance = null;
  private static FirebaseDatabase  database;
  private static FirebaseUser      currentUser;
  private        Context           context;
  private        DatabaseReference savedBookRef;
  private        DatabaseReference getBookRef;
  private        DatabaseReference deletedBookRef;
  private        DatabaseReference movedBookRef;


  public FirebaseRepo() {
  }

  public static FirebaseRepo getInstance() {
    if (instance == null) {
      instance = new FirebaseRepo();
      instance.initialize();
    }
    return instance;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  private void initialize() {
    database = FirebaseDatabase.getInstance();
    database.setPersistenceEnabled(true);
    currentUser = FirebaseAuth.getInstance().getCurrentUser();
  }

  public void saveBook(Book book, String nodeName) {
    savedBookRef = database.getReference()
        .child(currentUser.getUid())
        .child(nodeName.toLowerCase())
        .child(String.valueOf(book.getId()));

    savedBookRef.addValueEventListener(this);
    savedBookRef.setValue(book);
  }

  public DatabaseReference getBooksFromNodeReference(@BOOK_LISTS String nodeName) {
    getBookRef = database.getReference()
        .child(currentUser.getUid())
        .child(nodeName.toLowerCase());

    getBookRef.addValueEventListener(this);

    return getBookRef;
  }

  public void deleteBook(@BOOK_LISTS String nodeName, String bookId) {
    deletedBookRef = database.getReference()
        .child(currentUser.getUid())
        .child(nodeName.toLowerCase())
        .child(bookId);

    deletedBookRef.addValueEventListener(this);
    deletedBookRef.removeValue();
  }

  public void moveBook(@BOOK_LISTS String currentList, @BOOK_LISTS String listToMoveTo, Book book) {
//    deleteBook(currentList, String.valueOf(book.getId()));

    // the same function as the delete book is here
    // to prevent the addValueListener to code the updateWidget twice for an operation.
    database.getReference()
        .child(currentUser.getUid())
        .child(currentList.toLowerCase())
        .child(String.valueOf(book.getId()))
        .removeValue();

    saveBook(book, listToMoveTo);
  }

  private static void updateWidget(Context context) {
    ComponentName name   = new ComponentName(context, BooksWidgetProvider.class);
    int[]         ids    = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
    Intent        intent = new Intent(context, BooksWidgetProvider.class);
    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
    context.sendBroadcast(intent);
  }

  @Override
  public void onDataChange(DataSnapshot dataSnapshot) {
    Log.d(TAG, "onDataChange: going to update the widget");
    updateWidget(context);
  }

  @Override
  public void onCancelled(DatabaseError databaseError) {

  }
}
