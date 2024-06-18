package mapper;

import dto.BookDelete;
import dto.BookPost;
import dto.BookPut;
import model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BookMapperTest {

    @Test
    void bookPutToBook() {
        BookPut bookPut = new BookPut();
        bookPut.setBookId(1);
        bookPut.setTitle("book");
        bookPut.setAmount(11);
        bookPut.setPrice(111.11f);

        Book book = BookMapper.bookPutToBook(bookPut);

        Assertions.assertEquals(book.getBookId(), bookPut.getBookId());
        Assertions.assertEquals(book.getTitle(), bookPut.getTitle());
        Assertions.assertEquals(book.getAmount(), bookPut.getAmount());
        Assertions.assertEquals(book.getPrice(), bookPut.getPrice());
    }

    @Test
    void bookPostToBook() {
        BookPost bookPost = new BookPost();
        bookPost.setTitle("book");
        bookPost.setAmount(11);
        bookPost.setPrice(111.11f);

        Book book = BookMapper.bookPostToBook(bookPost);

        Assertions.assertEquals(book.getTitle(), bookPost.getTitle());
        Assertions.assertEquals(book.getAmount(), bookPost.getAmount());
        Assertions.assertEquals(book.getPrice(), bookPost.getPrice());
    }

    @Test
    void bookDeleteToBook() {
        BookDelete bookDelete = new BookDelete();
        bookDelete.setBookId(1);

        Book book = BookMapper.bookDeleteToBook(bookDelete);

        Assertions.assertEquals(book.getBookId(), bookDelete.getBookId());
    }
}