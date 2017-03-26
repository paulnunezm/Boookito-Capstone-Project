package com.nunez.bookito.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nunez.bookito.entities.Book;

import java.util.ArrayList;

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

  public static void getBooksFromNode(String nodeName) {
    Query booksQuery = database.getReference()
        .child(currentUser.getUid())
        .child(nodeName.toLowerCase());

    booksQuery.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {

        ArrayList<Book> books = new ArrayList<Book>();

        for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
          books.add(dataSnapshot.getValue(Book.class));
        }

//        TODO: Add return mechanism
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }
}
