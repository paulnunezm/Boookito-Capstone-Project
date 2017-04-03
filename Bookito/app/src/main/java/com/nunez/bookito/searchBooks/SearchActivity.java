package com.nunez.bookito.searchBooks;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nunez.bookito.R;
import com.nunez.bookito.customViews.BookTextWatcher;
import com.nunez.bookito.entities.Book;
import com.nunez.bookito.entities.BookWrapper;
import com.nunez.bookito.repositories.FirebaseRepo;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchBooksContract.View,
    SearchAdapter.SearchViewHolder.SearchBookListener, AddToModalBottomSheet.OnModalOptionSelected,
    LoaderManager.LoaderCallbacks<List<BookWrapper>> {

  private static final String TAG = "SearchActivity";

  private BookTextWatcher       mTextWatcher;
  private SearchPresenter       presenter;
  private SearchInteractor      interactor;
  private ProgressBar           progress;
  private RecyclerView          recyclerView;
  private GridLayoutManager     gridLayoutManager;
  private SearchAdapter         adapter;
  private AddToModalBottomSheet modalBottomSheet;
  private Book                  selectedBook;
  private View                  parentLayout;
  private FirebaseRepo          firebaseRepo;
  private String                searchFilter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_activity);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    EditText editText = (EditText) findViewById(R.id.editText);
    progress = (ProgressBar) findViewById(R.id.progress);
    recyclerView = (RecyclerView) findViewById(R.id.recycler);
    parentLayout = findViewById(R.id.layout);

    int gridColumns = getResources().getInteger(R.integer.search_activity_columns);
    gridLayoutManager = new GridLayoutManager(this,
        gridColumns,
        LinearLayoutManager.VERTICAL,
        false);

    mTextWatcher = new BookTextWatcher(this);
    interactor = new SearchInteractor(getApplication());
    presenter = new SearchPresenter(this, interactor);
    modalBottomSheet = new AddToModalBottomSheet();

    editText.addTextChangedListener(mTextWatcher);
    modalBottomSheet.setItemSelectedListener(this);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setHasFixedSize(true);

    // Prepare the loader.  Either re-connect with an existing one,
    // or start a new one.

    getSupportLoaderManager().initLoader(25927, null, this).forceLoad();

    // Prepare ad
    AdView    mAdView   = (AdView) findViewById(R.id.adView);
    AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
        .build();
    mAdView.loadAd(adRequest);
  }

  @Override
  public void showBooks(ArrayList<BookWrapper> booksArray) {
    adapter = new SearchAdapter(booksArray, this);
    recyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void showNoBooksFound() {
    Log.e(TAG, "showNoBooksFound: ");
  }

  @Override
  public void displayError() {
    Log.e(TAG, "displayError: ");
  }

  @Override
  public void showLoading() {
    recyclerView.setVisibility(View.GONE);
    progress.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    recyclerView.setVisibility(View.VISIBLE);
    progress.setVisibility(View.GONE);
  }


  /**
   * This is called everytime the text on the edit text is changed.
   * This is called by the {@link BookTextWatcher}.
   *
   * @param text {string} text changed in the edit text.
   */
  @Override
  public void onSearchTextChange(String text) {
//    presenter.searchBooks(text);
    Log.i(TAG, "onSearchTextChange: ");

    searchFilter = text;
    // This is a hack to make the Goodreads search engine work as expected.
    Log.d(TAG, "onSearchTextChange: "+searchFilter);

    // Make the loader perform another call and stop the previous one
    // if is still running.
    getSupportLoaderManager().restartLoader(25927, null, this).forceLoad();
  }

  @Override
  public void displaySnackBar(String message) {
    Snackbar.make(parentLayout, message, Snackbar.LENGTH_SHORT).show();
  }

  @Override
  public void onAddToClickListener(BookWrapper bookWrapper) {
    selectedBook = bookWrapper.getBook();
    selectedBook.setAverageRating(bookWrapper.getAverageRating());


    modalBottomSheet.show(getSupportFragmentManager(), "search_modal");
  }

  @Override
  public void onModalOptionSelected(String selectedItem) {
    if (selectedBook != null) presenter.saveBookTo(selectedBook, selectedItem);
    modalBottomSheet.dismiss();
    selectedBook = null;
  }


  @Override
  public Loader<List<BookWrapper>> onCreateLoader(int id, Bundle args) {
    Log.i(TAG, "onCreateLoader: ");
    return new SearchedBookLoader(this, searchFilter);
  }

  @Override
  public void onLoadFinished(Loader<List<BookWrapper>> loader, List<BookWrapper> data) {
    Log.i(TAG, "onLoadFinished: Activity");
    if (data != null && !data.isEmpty()) {
      adapter = new SearchAdapter((ArrayList<BookWrapper>) data, SearchActivity.this);
      recyclerView.setAdapter(adapter);
      Log.d(TAG, "onLoadFinished: Should re-populate the adapter");
    } else {
      // TODO: SHOW NO BOOKS
    }
    hideLoading();
  }

  @Override
  public void onLoaderReset(Loader<List<BookWrapper>> loader) {
    Log.i(TAG, "onLoaderReset: ");
    // this should clear the data in the adapter
    // but we don't want to, not at least the user
//    showLoading();
    getSupportLoaderManager().destroyLoader(25927);
  }
}
