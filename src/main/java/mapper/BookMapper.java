package mapper;

import dto.BookDelete;
import dto.BookPost;
import dto.BookPut;
import model.Book;

public class BookMapper {
    public static Book bookPutToBook(BookPut bookPut) {
        Book book = new Book();
        book.setBookId(bookPut.getBookId());
        book.setTitle(bookPut.getTitle());
        book.setPrice(bookPut.getPrice());
        book.setAmount(bookPut.getAmount());
        return book;
    }

    public static Book bookPostToBook(BookPost bookPost) {
        Book book = new Book();
        book.setTitle(bookPost.getTitle());
        book.setPrice(bookPost.getPrice());
        book.setAmount(bookPost.getAmount());
        return book;
    }

    public static Book bookDeleteToBook(BookDelete bookDelete) {
        Book book = new Book();
        book.setBookId(bookDelete.getBookId());
        return book;
    }
}
