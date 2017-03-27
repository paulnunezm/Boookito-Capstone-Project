package com.nunez.bookito.searchBooks;

import com.nunez.bookito.entities.Book;
import com.nunez.bookito.entities.BookWrapper;
import com.nunez.bookito.mvp.BaseContract;

import java.util.ArrayList;

/**
 * Created by paulnunez on 3/17/17.
 */

public interface SearchBooksContract {

  interface Presenter extends BaseContract.BasePresenter {
    void loadBooks(ArrayList<BookWrapper> books);
    void searchBooks(String bookTitle);
    void saveBookTo(Book book, String list);
  }

  interface View extends BaseContract.BaseView {
    void onSearchTextChange(String text);
  }

  interface Interactor extends BaseContract.BaseInteractor {
    void sendBooksToPresenter(ArrayList<BookWrapper> books);
    void searchBooks(String bookTitle) throws Exception;
    void saveBookTo(Book book, String nodeName);
  }
}
