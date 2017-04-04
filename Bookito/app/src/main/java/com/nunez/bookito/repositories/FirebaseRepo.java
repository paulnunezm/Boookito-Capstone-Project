package com.nunez.bookito.repositories;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
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
  private static FirebaseAnalytics mFirebaseAnalytics;
  private static FirebaseUser      currentUser;
  private        Context           context;
  private        DatabaseReference savedBookRef;
  private        DatabaseReference getBookRef;
  private        DatabaseReference deletedBookRef;


  public FirebaseRepo() {
  }

  public static FirebaseRepo getInstance() {
    if (instance == null) {
      instance = new FirebaseRepo();
      instance.initialize();
    }
    return instance;
  }

  public void setFirebaseAnalytics(FirebaseAnalytics mFirebaseAnalytics) {
    FirebaseRepo.mFirebaseAnalytics = mFirebaseAnalytics;
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
    logEvent("saved_book_to_" + nodeName, nodeName);
    saveBookToList(book, nodeName);
  }

  public DatabaseReference getBooksFromNodeReference(@BOOK_LISTS String nodeName) {

    if (currentUser == null) {
      currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

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

    saveBook(book, listToMoveTo, true);
  }

  public void getBookFromIdInList(@BOOK_LISTS String currentList, String bookId,
                                  final GetBookFromListListener bookListener) {
    final DatabaseReference reference = database.getReference()
        .child(currentUser.getUid())
        .child(currentList)
        .child(bookId);

    final ValueEventListener valueEventListener = new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        bookListener.onReceivedBook(dataSnapshot.getValue(Book.class));
        reference.removeEventListener(this);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        bookListener.onError();
        reference.removeEventListener(this);
      }
    };

    reference.addValueEventListener(valueEventListener);

  }

  public void logEvent(String eventType, String event) {
    Bundle bundle = new Bundle();
    bundle.putString(eventType, event);
    mFirebaseAnalytics.logEvent(eventType, bundle);
  }

  private static void updateWidget(Context context) {
    ComponentName name   = new ComponentName(context, BooksWidgetProvider.class);
    int[]         ids    = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
    Intent        intent = new Intent(context, BooksWidgetProvider.class);
    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
    context.sendBroadcast(intent);
  }

  private void saveBook(Book book, String nodeName, Boolean fromMoveBook) {
    logEvent("moved_book_to_" + nodeName, nodeName);
    saveBookToList(book, nodeName);
  }

  private void saveBookToList(Book book, String nodeName) {
    savedBookRef = database.getReference()
        .child(currentUser.getUid())
        .child(nodeName.toLowerCase())
        .child(String.valueOf(book.getId()));

    savedBookRef.addValueEventListener(this);
    savedBookRef.setValue(book);
  }

  @Override
  public void onDataChange(DataSnapshot dataSnapshot) {
    Log.d(TAG, "onDataChange: going to update the widget");
    updateWidget(context);
  }

  @Override
  public void onCancelled(DatabaseError databaseError) {

  }

  public interface GetBookFromListListener {
    void onReceivedBook(Book book);

    void onError();
  }
}
