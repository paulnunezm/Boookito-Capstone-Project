package com.nunez.bookito.mvp;

import com.nunez.bookito.entities.BookWrapper;

import java.util.ArrayList;

/**
 * Created by paulnunez on 3/26/17.
 */

public interface BaseContract {

  interface BasePresenter {
    void displayMessage(String message);
  }

  interface BaseView {
    void showBooks(ArrayList<BookWrapper> booksArray);
    void showNoBooksFound();
    void displayError();
    void showLoading();
    void hideLoading();
    void displaySnackBar(String message);
  }

  interface BaseInteractor {
    void setPresenter(BasePresenter presenter);
  }

}
