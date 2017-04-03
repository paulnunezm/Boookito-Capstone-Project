package com.nunez.bookito.searchBooks;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nunez.bookito.R;
import com.nunez.bookito.repositories.FirebaseNodes;

/**
 * Created by paulnunez on 3/24/17.
 */

public class AddToModalBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {
  OnModalOptionSelected listener;


  interface OnModalOptionSelected {
    void onModalOptionSelected(String selectedItem);
  }

  static AddToModalBottomSheet newInstace() {
    return new AddToModalBottomSheet();
  }

  public AddToModalBottomSheet() {
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.search_activity_add_to_modal, container, false);

    Resources res = getContext().getResources();

    v.findViewById(R.id.container).setContentDescription(res.getString(R.string.search_activity_add_to));

    View btnWishList = v.findViewById(R.id.btn_wishlist);
    View btnMyBook   = v.findViewById(R.id.btn_my_books);

    // Set content descriptions
    setButtonContentDescription(btnWishList, R.string.search_activity_add_to);
    setButtonContentDescription(btnMyBook, R.string.search_activity_my_books);

    // Set listeners
    btnWishList.setOnClickListener(this);
    btnMyBook.setOnClickListener(this);

    return v;
  }

  private void setButtonContentDescription(View v, int stringResouce) {
    v.setContentDescription(
        getResources().getString(R.string.accessibility_search_activity_add_to, // Format string
            getResources().getString(stringResouce))
    );
  }

  public void setItemSelectedListener(OnModalOptionSelected listener) {
    this.listener = listener;
  }

  // Implementations
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_wishlist:
        listener.onModalOptionSelected(FirebaseNodes.WISHLIST);
        break;
      case R.id.btn_my_books:
        listener.onModalOptionSelected(FirebaseNodes.MY_BOOKS);
        break;
    }
  }

}
