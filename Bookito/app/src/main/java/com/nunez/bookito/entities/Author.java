package com.nunez.bookito.entities;

import org.simpleframework.xml.Root;

/**
 * Created by paulnunez on 3/20/17.
 */

@Root(name = "author", strict =  false)
public class Author{
  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}