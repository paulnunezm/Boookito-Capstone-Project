package com.nunez.bookito.searchBooks;

import com.nunez.bookito.entities.Book;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static com.nunez.bookito.searchBooks.SearchBooksContract.View;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by paulnunez on 3/17/17.
 */
public class SearchPresenterTest {

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  SearchBooksContract.Interactor interactor;

  @Mock
  View view;

  SearchPresenter presenter;
  ArrayList<Book> MANY_BOOKS;
  final String QUERY = "Dracula";

  @Before
  public void setUp(){
    presenter = new SearchPresenter(view, interactor);
    MANY_BOOKS = new ArrayList<>();
    MANY_BOOKS.add(new Book());
    MANY_BOOKS.add(new Book());
    MANY_BOOKS.add(new Book());
  }

  @Test
  public void shouldReturnBooks(){
    // given
//    when(interactor.searchBooks(QUERY));//.thenReturn(MANY_BOOKS);

//    SearchBooksContract.Interactor interactor = Spy(interactor.searchBooks(QUERY))

    // when
//    verify(interactor.searchBooks(QUERY));
    presenter.searchBooks(QUERY);
//    verify(interactor.searchBooks(QUERY));
//    Mockito.verify(interactor.searchBooks(QUERY));


//    presenter.loadBooks(MANY_BOOKS);

    // then
    verify(view).showLoading();
    verify(view).hideLoading();
//    verify(view).showBooks(MANY_BOOKS);
  }

  @Test
  public void noBooksFoundShouldPass(){
    //given
    final ArrayList<Book> EMPTY_LIST = new ArrayList<>();
//    when(interactor.searchBooks(QUERY)).thenReturn(EMPTY_LIST);

    //when
    presenter.searchBooks(QUERY);
//    presenter.loadBooks(EMPTY_LIST);

    // then
    verify(view).showLoading();
    verify(view).showNoBooksFound();
  }

  @Test
  public void shouldHandleRepositoryErrors(){
//    when(booksRepository.getBooks()).thenReturn(Single.<List<Book>>error(new Throwable("boom")));
    //given
//    when(interactor.searchBooks(QUERY)).thenThrow(new RuntimeException("books falled apart!!"));

    //when
    presenter.searchBooks(QUERY);

    //then
    verify(view).showLoading();
    verify(view).hideLoading();
    verify(view).displayError();
  }

  @Test
  public void shouldPassNotQuerySended(){
    //when
    presenter.searchBooks("");

    //then
    verify(view, never()).showLoading();
    verify(interactor, never()).searchBooks(""); //never called if string is empty
  }

  @Test
  public void shouldPassSpaceQuerySended(){
    //when
    presenter.searchBooks("   ");

    //then
    verify(view, never()).showLoading();
    verify(interactor, never()).searchBooks(""); //never called if string is empty
  }
}