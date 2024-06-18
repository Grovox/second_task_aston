package services;

import dto.BuyBookDelete;
import dto.BuyBookPost;
import junit.framework.Assert;
import model.Book;
import model.BuyBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.BookRepository;
import repo.BuyBookRepository;
import repo.impl.BookRepositoryImpl;
import repo.impl.BuyBookRepositoryImpl;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class BuyBookServiceTest {
    BuyBookService service;
    BookRepository bookRepository;
    BuyBookRepository buyBookRepository;

    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        service = BuyBookService.getInstance();
        bookRepository = mock(BookRepositoryImpl.class);
        buyBookRepository = mock(BuyBookRepositoryImpl.class);

        Field field = BuyBookService.class.getDeclaredField("bookRepository");
        field.setAccessible(true);
        field.set(service, bookRepository);

        field = BuyBookService.class.getDeclaredField("buyBookRepository");
        field.setAccessible(true);
        field.set(service, buyBookRepository);
    }
    @Test
    void getInstance() {
        Assert.assertEquals(BuyBookService.getInstance().getClass(), BuyBookService.class);
    }

    @Test
    void getAllBuyBooks() {

        service.getAllBuyBooks();

        verify(buyBookRepository).getAll();
    }

    @Test
    void haveBuyBookById() {
        when(buyBookRepository.findById(anyInt())).thenReturn(null);

        boolean ans = service.haveBuyBookById(anyInt());

        verify(buyBookRepository).findById(anyInt());
        Assert.assertFalse(ans);
    }

    @Test
    void changeBuyBook() {
        BuyBook buyBook = new BuyBook();
        when(buyBookRepository.findById(anyInt())).thenReturn(new BuyBook());
        when(bookRepository.findById(anyInt())).thenReturn(new Book());

        service.changeBuyBook(buyBook);

        verify(buyBookRepository).findById(anyInt());
        verify(bookRepository).findById(anyInt());
        verify(bookRepository).update(any(Book.class));
        verify(buyBookRepository).update(any(BuyBook.class));
    }

    @Test
    void addBuyBook() {
        BuyBookPost buyBookPost = new BuyBookPost();
        when(bookRepository.findById(anyInt())).thenReturn(new Book());

        service.addBuyBook(buyBookPost);

        verify(bookRepository).findById(anyInt());
        verify(bookRepository).update(any(Book.class));
        verify(buyBookRepository).save(any(BuyBook.class));
    }

    @Test
    void deleteBuyBook() {
        BuyBookDelete buyBookDelete = new BuyBookDelete();

        service.deleteBuyBook(buyBookDelete);

        verify(buyBookRepository).delete(any(BuyBook.class));
    }
}