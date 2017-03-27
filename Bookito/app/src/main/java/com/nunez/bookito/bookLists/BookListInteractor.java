package com.nunez.bookito.bookLists;

import android.app.Application;

import com.nunez.bookito.BookitoApp;
import com.nunez.bookito.mvp.BaseContract;
import com.nunez.bookito.repositories.FirebaseNodes;
import com.nunez.bookito.repositories.FirebaseRepo;

/**
 * Created by paulnunez on 3/26/17.
 */

class BookListInteractor implements BookListContract.Interactor {

  private Application       app;
  private BookListPresenter presenter;

  public BookListInteractor(Application app) {
    this.app = (BookitoApp) app;
  }

  @Override
  public void getBookFromList(@FirebaseNodes.BOOK_LISTS String bookListName) {
    presenter.SendDbReferenceToView(FirebaseRepo.getBooksFromNodeReference(bookListName));
  }

  @Override
  public void moveBookTo(@FirebaseNodes.BOOK_LISTS String bookListName) {

  }

  @Override
  public void deleteBook(@FirebaseNodes.BOOK_LISTS String bookListName) {

  }

  @Override
  public void setPresenter(BaseContract.BasePresenter presenter) {
    this.presenter = (BookListPresenter) presenter;
  }
}
