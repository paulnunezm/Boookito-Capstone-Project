package com.nunez.bookito.searchBooks;

import com.nunez.bookito.entities.BookWrapper;

import java.util.ArrayList;

/**
 * Created by paulnunez on 3/17/17.
 */

public interface SearchBooksContract {
  interface Presenter {
    void searchBooks(String bookTitle);
    void loadBooks(ArrayList<BookWrapper> books);
  }

  interface View{
    void showBooks(ArrayList<BookWrapper> booksArray);
    void showNoBooksFound();
    void displayError();
    void showLoading();
    void hideLoading();
  }

  interface Interactor {
    void searchBooks(String bookTitle);
    void setPresenter(SearchPresenter presenter);
    void sendBooksToPresenter(ArrayList<BookWrapper> books);
  }
}
