package com.nunez.bookito.searchBooks;

import com.nunez.bookito.models.Book;

import java.util.ArrayList;

/**
 * Created by paulnunez on 3/17/17.
 */

public interface SearchBooksContract {
  interface Presenter {
    void searchBooks(String bookTitle);
    void loadBooks(ArrayList<Book> books);
  }

  interface View{
    void showBooks(ArrayList<Book> booksArray);
    void showNoBooksFound();
    void displayError();

    void showLoading();

    void hideLoading();
  }

  interface Repository{
    ArrayList<Book> searchBooks(String bookTitle);
  }
}
