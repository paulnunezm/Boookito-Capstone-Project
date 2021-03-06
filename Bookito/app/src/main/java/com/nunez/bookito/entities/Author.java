package com.nunez.bookito.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by paulnunez on 3/20/17.
 */

@Root(name = "author", strict = false)
public class Author {

  @Element(name = "id")
  public int id;

  @Element(name = "name")
  public String name;

  public Author() {
  }

  public Author(@Element(name = "id") int id, @Element(name = "name") String name) {
    this.id = id;
    this.name = name;
  }

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