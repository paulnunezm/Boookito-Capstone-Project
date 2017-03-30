package com.nunez.bookito.bookLists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.nunez.bookito.R;
import com.nunez.bookito.entities.Book;
import com.nunez.bookito.entities.BookWrapper;
import com.nunez.bookito.repositories.FirebaseNodes;

import java.util.ArrayList;

/**
 * Created by paulnunez on 3/26/17.
 */

public class BookListFragment extends Fragment implements BookListContract.View, BookListAdapter.onShowListListerner, BookListViewHolder.BookListItemListener, BookListsModalBottomSheet.OnModalOptionSelected {
  /**
   * This fragment present a list of the selected book list.
   */

  private              String TAG           = "BookListFragment";
  private static final String ARG_LIST_NAME = "list_name";

  private BookListPresenter         presenter;
  private BookListInteractor        interactor;
  private RecyclerView              recyclerView;
  private FirebaseRecyclerAdapter   adapter;
  private GridLayoutManager         layoutManager;
  private FrameLayout               layout;
  private ProgressBar               progressBar;
  private BookListsModalBottomSheet modalBottomSheet;
  private Book                      selectedBook;
  private String                    currentListName;
  private boolean                   isListShowed;

  public BookListFragment() {
  }

  /**
   * Returns a new instance of this fragment for the given the
   * Firebase list node name.
   */
  public static BookListFragment newInstance(@FirebaseNodes.BOOK_LISTS String selectedList) {
    BookListFragment fragment = new BookListFragment();

    // set correct debug tag name
    fragment.TAG = fragment.TAG.concat("_" + selectedList);

    // create the bundle
    Bundle args = new Bundle();
    args.putString(ARG_LIST_NAME, selectedList);
    fragment.setArguments(args);
    return fragment;
  }


  @SuppressWarnings("WrongConstant")
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.book_lists_fragment, container, false);

    layout = (FrameLayout) rootView.findViewById(R.id.layout);
    recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
    progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

    currentListName = getArguments().getString(ARG_LIST_NAME);

    interactor = new BookListInteractor(getActivity().getApplication());
    presenter = new BookListPresenter(this, interactor);

    modalBottomSheet = BookListsModalBottomSheet.newInstace(currentListName);
    layoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.book_list_fragment_columns));
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setHasFixedSize(true);
    presenter.getBookFromList(currentListName);

    return rootView;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    adapter.cleanup();
  }

  @Override
  public void showBooks(ArrayList<BookWrapper> booksArray) {
  }

  @Override
  public void showNoBooksFound() {
  }

  @Override
  public void displayError() {

  }

  @Override
  public void showLoading() {
    Log.d(TAG, "showLoading: ");
    recyclerView.setVisibility(View.INVISIBLE);
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    Log.d(TAG, "hideLoading: ");
    recyclerView.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void displaySnackBar(String message) {
    Snackbar.make(layout, message, Snackbar.LENGTH_SHORT).show();
  }

  @Override
  public void setupRecyclerViewWithReference(DatabaseReference bookListRef) {
    adapter = new BookListAdapter(bookListRef, this, this);
    recyclerView.setAdapter(adapter);
  }

  /**
   * This is not with the mvp structure as we need a callback
   * from the adapter to let us know when we should hide the loading.
   */
  @Override
  public void onListShowed() {
    if (!isListShowed) {
      Log.d(TAG, "onListShowed() called");
      isListShowed = true;
      hideLoading();
    }
  }

  @Override
  public void onItemClickListener(Book book) {
    selectedBook = book;
    modalBottomSheet.show(getActivity().getSupportFragmentManager(), "item_modal");
    modalBottomSheet.setItemSelectedListener(this);
  }

  @Override
  public void onModalOptionSelected(String optionSelected) {
    presenter.bookListOperation(optionSelected, currentListName, selectedBook);
    selectedBook = null;
    modalBottomSheet.dismiss();
  }
}
