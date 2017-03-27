package com.nunez.bookito.bookLists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nunez.bookito.R;
import com.nunez.bookito.repositories.FirebaseNodes;
/**
 * Created by paulnunez on 3/26/17.
 */

/**
 * A bottom sheet modal to handle to handle the lists books options
 * as delete, list 1, list 2...
 * <p>
 * This modal have two actions one for delete and one dynamic that can be set
 * within new instance constructor and then delegate the behavior to the {@link OnItemSelectedListener}
 */
public class BookListsModalBottomSheet extends BottomSheetDialogFragment {
  public static final String ARG_CURRENT_LIST = "current_list";

  OnItemSelectedListener listener;

  public interface OnItemSelectedListener {
    void OnModalItemSelected(String selectedItem);
  }

  static BookListsModalBottomSheet newInstace(@FirebaseNodes.BOOK_LISTS String currentListName) {
    BookListsModalBottomSheet fragment = new BookListsModalBottomSheet();

    // create the bundle
    Bundle args = new Bundle();
    args.putString(ARG_CURRENT_LIST, currentListName);
    fragment.setArguments(args);
    return fragment;
  }

  public BookListsModalBottomSheet() {
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.book_list_modal, container, false);

    final String action = getArguments().getString(ARG_CURRENT_LIST);

    v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.OnModalItemSelected(getResources().getString(R.string.book_list_modal_delete));
      }
    });

    v.findViewById(R.id.btn_action).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.OnModalItemSelected(action);
      }
    });

    return v;
  }

  public void setItemSelectedListener(OnItemSelectedListener listener) {
    this.listener = listener;
  }
}
