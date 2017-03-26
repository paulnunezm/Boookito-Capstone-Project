package com.nunez.bookito.bookLists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nunez.bookito.R;
import com.nunez.bookito.repositories.FirebaseNodes;

/**
 * Created by paulnunez on 3/26/17.
 */

public class BookListFragment extends Fragment{
  /**
   *  This fragment present a list of the selected book list.
   */

  private String TAG = "BookListFragment";
  private static final String ARG_LIST_NAME = "list_name";

  public BookListFragment() {
  }
  /**
   * Returns a new instance of this fragment for the given the
   * Firebase list node name.
   */
  public static BookListFragment newInstance(@FirebaseNodes.BOOK_LISTS String selectedList){
    BookListFragment fragment = new BookListFragment();

    // set correct debug tag name
    fragment.TAG = fragment.TAG.concat("_"+selectedList);

    // create the bundle
    Bundle              args     = new Bundle();
    args.putString(ARG_LIST_NAME, selectedList);
    fragment.setArguments(args);
    return fragment;
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View     rootView = inflater.inflate(R.layout.main_activity_fragment, container, false);

    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
    textView.setText(getArguments().getString(ARG_LIST_NAME));
    return rootView;
  }
}
