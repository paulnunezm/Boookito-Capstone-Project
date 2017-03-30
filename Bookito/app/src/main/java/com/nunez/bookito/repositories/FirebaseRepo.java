package com.nunez.bookito.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nunez.bookito.entities.Book;
import com.nunez.bookito.repositories.FirebaseNodes.BOOK_LISTS;

/**
 * Created by paulnunez on 3/25/17.
 */

public class FirebaseRepo {
  private static final String TAG = "FirebaseRepo";

  private static FirebaseDatabase database;
  private static FirebaseUser     currentUser;

  public void initialize() {
    database = FirebaseDatabase.getInstance();
    currentUser = FirebaseAuth.getInstance().getCurrentUser();
  }

  public static void saveBook(Book book, String nodeName) {
    database.getReference()
        .child(currentUser.getUid())
        .child(nodeName.toLowerCase())
        .child(String.valueOf(book.getId()))
        .setValue(book);
  }

  public static DatabaseReference getBooksFromNodeReference(@BOOK_LISTS  String nodeName) {
    DatabaseReference bookListReference = database.getReference()
        .child(currentUser.getUid())
        .child(nodeName.toLowerCase());

    return bookListReference;
  }

  public static void deleteBook(@BOOK_LISTS String nodeName, String bookId ){
   database.getReference()
        .child(currentUser.getUid())
        .child(nodeName.toLowerCase())
        .child(bookId)
        .removeValue();
  }

  public static void moveBook(@BOOK_LISTS String currentList, @BOOK_LISTS String listToMoveTo, Book book) {
    deleteBook(currentList, String.valueOf(book.getId()));
    saveBook(book, listToMoveTo);
  }
}
