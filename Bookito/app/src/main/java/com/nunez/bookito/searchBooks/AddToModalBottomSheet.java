package com.nunez.bookito.searchBooks;

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

public class AddToModalBottomSheet extends BottomSheetDialogFragment {
  OnItemSelectedListener listener;

  public interface OnItemSelectedListener{
    void OnModalItemSelected(String selectedItem);
  }

  static AddToModalBottomSheet newInstace(){
    return new AddToModalBottomSheet();
  }

  public AddToModalBottomSheet(){}

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//    return super.onCreateView(inflater, container, savedInstanceState);
    View v = inflater.inflate(R.layout.search_activity_add_to_modal, container, false);

    v.findViewById(R.id.btn_wishlist).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.OnModalItemSelected(FirebaseNodes.WISHLIST);
      }
    });

    v.findViewById(R.id.btn_my_books).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.OnModalItemSelected(FirebaseNodes.MY_BOOKS);
      }
    });

    return v;
  }

  public void setItemSelectedListener(OnItemSelectedListener listener){
    this.listener = listener;
  }
}
