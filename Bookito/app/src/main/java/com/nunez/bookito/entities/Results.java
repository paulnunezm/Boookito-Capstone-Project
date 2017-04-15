package com.nunez.bookito.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by paulnunez on 3/20/17.
 */

@Root(name = "results", strict = false)
public class Results{

  @ElementList(inline = true)
  private ArrayList<BookWrapper> bookWrappers;

  public Results() {
  }

  public Results(ArrayList<BookWrapper> bookWrappers) {
    this.bookWrappers = bookWrappers;
  }

  public ArrayList<BookWrapper> getBookWrappers() {
    return bookWrappers;
  }

  public void setBookWrappers(ArrayList<BookWrapper> bookWrappers) {
    this.bookWrappers = bookWrappers;
  }
}