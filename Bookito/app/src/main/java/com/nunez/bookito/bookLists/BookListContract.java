package com.nunez.bookito.bookLists;

import com.google.firebase.database.DatabaseReference;
import com.nunez.bookito.mvp.BaseContract;

import static com.nunez.bookito.repositories.FirebaseNodes.BOOK_LISTS;

/**
 * Created by paulnunez on 3/26/17.
 */

interface BookListContract {

  interface Presenter extends BaseContract.BasePresenter{
    void getBookFromList(@BOOK_LISTS String bookListName);
    void moveBookTo(@BOOK_LISTS String bookListName);
    void deleteBook(@BOOK_LISTS String bookListName);

    void SendDbReferenceToView(DatabaseReference bookListRef);
  }

  interface View extends BaseContract.BaseView{
    void setupRecyclerViewWithReference(DatabaseReference bookListRef);
  }

  interface Interactor extends BaseContract.BaseInteractor{
    void getBookFromList(@BOOK_LISTS String bookListName);
    void moveBookTo(@BOOK_LISTS String bookListName);
    void deleteBook(@BOOK_LISTS String bookListName);
  }

}
