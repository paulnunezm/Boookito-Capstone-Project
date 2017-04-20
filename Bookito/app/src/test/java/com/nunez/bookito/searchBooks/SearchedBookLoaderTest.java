package com.nunez.bookito.searchBooks;

import com.nunez.bookito.entities.BookWrapper;
import com.nunez.bookito.entities.GoodreadsResponse;

import junit.framework.Assert;

import org.intellij.lang.annotations.Language;
import org.junit.Test;
import org.simpleframework.xml.core.Persister;

/**
 * Created by paulnunez on 4/20/17.
 */
public class SearchedBookLoaderTest {

  @Language("XML")
  String xml = "<GoodreadsResponse>\n" +
      "<Request>\n" +
      "<authentication>true</authentication>\n" +
      "<key>\n" +
      "<![CDATA[ 9kdgMNGvbCA4Odn3u9f72g ]]>\n" +
      "</key>\n" +
      "<method>\n" +
      "<![CDATA[ search_index ]]>\n" +
      "</method>\n" +
      "</Request>\n" +
      "<search>\n" +
      "<query>\n" +
      "<![CDATA[ Ender's Game ]]>\n" +
      "</query>\n" +
      "<results-start>1</results-start>\n" +
      "<results-end>20</results-end>\n" +
      "<total-results>511</total-results>\n" +
      "<source>Goodreads</source>\n" +
      "<query-time-seconds>0.14</query-time-seconds>\n" +
      "<results>\n" +
      "<work>\n" +
      "<id type=\"integer\">2422333</id>\n" +
      "<books_count type=\"integer\">218</books_count>\n" +
      "<ratings_count type=\"integer\">846667</ratings_count>\n" +
      "<text_reviews_count type=\"integer\">37382</text_reviews_count>\n" +
      "<original_publication_year type=\"integer\">1985</original_publication_year>\n" +
      "<original_publication_month type=\"integer\" nil=\"true\"/>\n" +
      "<original_publication_day type=\"integer\" nil=\"true\"/>\n" +
      "<average_rating>4.29</average_rating>\n" +
      "<best_book type=\"Book\">\n" +
      "<id type=\"integer\">375802</id>\n" +
      "<title>Ender's Game (Ender's Saga, #1)</title>\n" +
      "<author>\n" +
      "<id type=\"integer\">589</id>\n" +
      "<name>Orson Scott Card</name>\n" +
      "</author>\n" +
      "<image_url>\n" +
      "https://images.gr-assets.com/books/1408303130m/375802.jpg\n" +
      "</image_url>\n" +
      "<small_image_url>\n" +
      "https://images.gr-assets.com/books/1408303130s/375802.jpg\n" +
      "</small_image_url>\n" +
      "</best_book>\n" +
      "</work>\n" +
      "<work>\n" +
      "<id type=\"integer\">938064</id>\n" +
      "<books_count type=\"integer\">55</books_count>\n" +
      "<ratings_count type=\"integer\">71550</ratings_count>\n" +
      "<text_reviews_count type=\"integer\">634</text_reviews_count>\n" +
      "<original_publication_year type=\"integer\">1984</original_publication_year>\n" +
      "<original_publication_month type=\"integer\">12</original_publication_month>\n" +
      "<original_publication_day type=\"integer\" nil=\"true\"/>\n" +
      "<average_rating>4.17</average_rating>\n" +
      "<best_book type=\"Book\">\n" +
      "<id type=\"integer\">44687</id>\n" +
      "<title>Enchanters' End Game (The Belgariad, #5)</title>\n" +
      "<author>\n" +
      "<id type=\"integer\">8732</id>\n" +
      "<name>David Eddings</name>\n" +
      "</author>\n" +
      "<image_url>\n" +
      "https://images.gr-assets.com/books/1217735909m/44687.jpg\n" +
      "</image_url>\n" +
      "<small_image_url>\n" +
      "https://images.gr-assets.com/books/1217735909s/44687.jpg\n" +
      "</small_image_url>\n" +
      "</best_book>\n" +
      "</work>\n" +
      "<work>\n" +
      "<id type=\"integer\">42437887</id>\n" +
      "<books_count type=\"integer\">3</books_count>\n" +
      "<ratings_count type=\"integer\">4808</ratings_count>\n" +
      "<text_reviews_count type=\"integer\">591</text_reviews_count>\n" +
      "<original_publication_year type=\"integer\">2015</original_publication_year>\n" +
      "<original_publication_month type=\"integer\">9</original_publication_month>\n" +
      "<original_publication_day type=\"integer\">8</original_publication_day>\n" +
      "<average_rating>4.10</average_rating>\n" +
      "<best_book type=\"Book\">\n" +
      "<id type=\"integer\">22874150</id>\n" +
      "<title>The End Game</title>\n" +
      "<author>\n" +
      "<id type=\"integer\">6876994</id>\n" +
      "<name>Kate McCarthy</name>\n" +
      "</author>\n" +
      "<image_url>\n" +
      "https://images.gr-assets.com/books/1423089153m/22874150.jpg\n" +
      "</image_url>\n" +
      "<small_image_url>\n" +
      "https://images.gr-assets.com/books/1423089153s/22874150.jpg\n" +
      "</small_image_url>\n" +
      "</best_book>\n" +
      "</work>\n" +
      "</results>\n" +
      "</search>\n" +
      "</GoodreadsResponse>";


  @Test
  public void assertAuthorParsing() throws Exception {
    Persister               persister   = new Persister();
    final GoodreadsResponse response    = persister.read(GoodreadsResponse.class, xml);
    final BookWrapper       bookWrapper = response.getSearch().getResults().getBookWrappers().get(0);
    final String otherBookAuthorName = response.getSearch().getResults()
        .getBookWrappers().get(1).getBook().getAuthor().getName();

    Assert.assertEquals("Orson Scott Card", bookWrapper.getBook().getAuthor().getName());
    Assert.assertEquals("David Eddings", otherBookAuthorName);
  }
}