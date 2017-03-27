package com.nunez.bookito.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nunez.bookito.entities.Book;

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

  public static DatabaseReference getBooksFromNodeReference(@FirebaseNodes.BOOK_LISTS  String nodeName) {
    DatabaseReference bookListReference = database.getReference()
        .child(currentUser.getUid())
        .child(nodeName.toLowerCase());

    return bookListReference;
  }
}
