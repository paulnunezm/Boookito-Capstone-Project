package com.nunez.bookito.searchBooks

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.nunez.bookito.R
import com.nunez.bookito.customViews.BookTextWatcher
import com.nunez.bookito.customViews.EndlessScrollListener
import com.nunez.bookito.entities.Book
import com.nunez.bookito.entities.BookWrapper
import kotlinx.android.synthetic.main.no_books_screen_indicator.*
import kotlinx.android.synthetic.main.search_activity.*
import kotlinx.android.synthetic.main.search_activity_content.*

class SearchActivity : AppCompatActivity(), SearchBooksContract.View,
        SearchAdapter.SearchViewHolder.SearchBookListener,
        AddToModalBottomSheet.OnModalOptionSelected,
        LoaderManager.LoaderCallbacks<List<BookWrapper>> {

    companion object {
        private val TAG = "SearchActivity"
    }

    // This are lateinit vars because we can't initialize them in the constructor
    private lateinit var mTextWatcher: BookTextWatcher

    // Lazy delegated vars get initialize when needed
    private val modalBottomSheet: AddToModalBottomSheet by lazy { AddToModalBottomSheet() }

    private lateinit var presenter: SearchBooksContract.Presenter
    private lateinit var interactor: SearchInteractor
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: SearchAdapter
    private var selectedBook: Book? = null
    private var mHasUserSearch: Boolean = false
    private var isLoadingFromBottomScrollListener = false
    private var searchFilter: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        val bundle = Bundle()
        bundle.putString("pu", "pa") // can't send null for some reason
        supportLoaderManager.initLoader(25927, bundle, this).forceLoad()

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val gridColumns = resources.getInteger(R.integer.search_activity_columns)
        gridLayoutManager = GridLayoutManager(this,
                gridColumns,
                LinearLayoutManager.VERTICAL,
                false)

        // initialize objects
        interactor = SearchInteractor(application)
        presenter = SearchPresenter(this, interactor)
        adapter = SearchAdapter(this@SearchActivity)
        mTextWatcher = BookTextWatcher { onSearchTextChange(it) } // set listener with lambda

        // set values, properties and listeners
        no_books_indicator_text.text = resources.getString(R.string.search_activity_search_book_msg)
        editText.addTextChangedListener(mTextWatcher)
        recycler.layoutManager = gridLayoutManager
        recycler.setHasFixedSize(true)

        recycler.addOnScrollListener(EndlessScrollListener {
            if (!isLoadingFromBottomScrollListener) {
                isLoadingFromBottomScrollListener = true
                Log.d("SearchAtivity", "reached_bottom")
            }
        })

        // Prepare ad
        val adRequest = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .build()

        // show ad
        adView.loadAd(adRequest)
    }

    override fun showBooks(booksArray: ArrayList<BookWrapper>) {
//        adapter = SearchAdapter(booksArray, this)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun showNoBooksFound() {
        Log.e(TAG, "showNoBooksFound: ")
    }

    override fun displayError() {
        Log.e(TAG, "displayError: ")
    }

    override fun showLoading() {
        recycler.visibility = View.GONE
        progress.visibility = View.VISIBLE
        no_books_indicator.visibility = View.GONE
    }

    override fun hideLoading() {
        recycler.visibility = View.VISIBLE
        progress.visibility = View.GONE
        no_books_indicator.visibility = View.GONE
    }

    /**
     * This is called everytime the text on the edit text is changed.
     * This is called by the [BookTextWatcher].

     * @param text {string} text changed in the edit text.
     */
    override fun onSearchTextChange(text: String) {
        //presenter.searchBooks(text);
        //TODO: pass this logic to the SearchInteractor.

        if (!mHasUserSearch) {
            mHasUserSearch = true
            no_books_indicator_text.text = resources.getString(R.string.search_activity_no_books_message)
        }

        searchFilter = text
        showLoading()

        // Make the loader perform another call and stop the previous one
        // if is still running.
        val bundle = Bundle()
        bundle.putString("pu", "pa") // can't send null for some reason
        supportLoaderManager.restartLoader(25927, bundle, this).forceLoad()
    }

    override fun displaySnackBar(message: String) {
        Snackbar.make(layout, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onAddToClickListener(bookWrapper: BookWrapper) {
        selectedBook = bookWrapper.getBook()
        selectedBook?.averageRating = bookWrapper.getAverageRating()

        modalBottomSheet.setItemSelectedListener(this)
        modalBottomSheet.show(supportFragmentManager, "search_modal")
    }

    override fun onModalOptionSelected(selectedItem: String) {
        if (selectedBook != null) presenter.saveBookTo(selectedBook, selectedItem)
        modalBottomSheet.dismiss()
        selectedBook = null
    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<List<BookWrapper>> {
        return SearchedBookLoader(this, searchFilter)
    }

    override fun onLoadFinished(loader: Loader<List<BookWrapper>>, data: List<BookWrapper>?) {
        hideLoading()
        adapter.clear()

        if (data != null && !data.isEmpty()) {
            recycler.adapter = adapter
            adapter.addBooks(data as ArrayList<BookWrapper>)
        } else {
            no_books_indicator.visibility = View.VISIBLE
        }
    }

    override fun onLoaderReset(loader: Loader<List<BookWrapper>>) {
        Log.i(TAG, "onLoaderReset: ")
        // this should clear the data in the adapter
        // but we don't want to, not at least the user
        supportLoaderManager.destroyLoader(25927)
    }
}
