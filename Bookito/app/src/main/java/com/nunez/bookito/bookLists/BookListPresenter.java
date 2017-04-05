package com.nunez.bookito.bookLists;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.nunez.bookito.entities.Book;
import com.nunez.bookito.repositories.FirebaseNodes;

/**
 * Created by paulnunez on 3/26/17.
 */

class BookListPresenter implements BookListContract.Presenter {
  private static final String TAG = "BookListPresenter";

  private final BookListContract.View view;
  private final BookListContract.Interactor interactor;

  public BookListPresenter(BookListContract.View view, BookListContract.Interactor interactor) {
    this.view = view;
    this.interactor = interactor;
    interactor.setPresenter(this);
  }

  @Override
  public void displayMessage(String message) {
    Log.d(TAG, "displayMessage() called with: message = [" + message + "]");
    view.displaySnackBar(message);
  }

  @Override
  public void getBookFromList(@FirebaseNodes.BOOK_LISTS String bookListName) {
    view.showLoading();
    interactor.getBookFromList(bookListName);
  }

  @Override
  public void bookListOperation(String option, String currentList, Book selectedBook) {
    if(option.equals(BookListsModalBottomSheet.OPTION_DELETE)){
      interactor.deleteBook(currentList, selectedBook);
    }else{
      interactor.moveBookTo(currentList, option, selectedBook);
    }
  }

  /**
   * Sends the builded query to the view in wich the recyclerView
   * will handle this extending the {@link }
   */
  @Override
  public void SendDbReferenceToView(DatabaseReference bookListRef) {
    //view.hideLoading();
    // the hide loading will be trigger within a callback added to the adapter;
    view.setupRecyclerViewWithReference(bookListRef);
  }

  @Override
  public void showNoBooksFound() {
    view.showNoBooksFound();
  }

  @Override
  public void showBooks() {
    view.hideLoading();
  }


}
