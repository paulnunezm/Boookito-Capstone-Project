package com.nunez.bookito.repositories;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by paulnunez on 3/26/17.
 */

public class FirebaseNodes {

  @Retention(RetentionPolicy.CLASS)
  @StringDef({WISHLIST, MY_BOOKS})
  public@interface BOOK_LISTS{}
  public static final String WISHLIST = "whislist";
  public static final String MY_BOOKS = "myBooks";
}
